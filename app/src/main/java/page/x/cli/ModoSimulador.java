package page.x.cli;

import page.x.Maquina;
import page.x.PageX;
import page.x.TLB.TLB;
import page.x.TLB.TlbEntry;
import page.x.TLB.algoritmos.substituicao.*;
import page.x.interruptions.Interruption;
import page.x.memoriafisica.MemoriaFisica;

import java.util.Scanner;

public class ModoSimulador {
    private PageX pagex;
    private Scanner sc = new Scanner(System.in);
    private Maquina maquina;
    private TLB tlb;
    private MemoriaFisica memoriaFisica;

    public ModoSimulador(PageX pagex) {
        this.pagex = pagex;
    }

    public void maquinaSetUp() {
        System.out.println("\n===============================");
        System.out.println(" CONFIGURAÇÃO DA MÁQUINA INICIADA");
        System.out.println("===============================\n");
    
        System.out.println("Escolha a quantidade de bits de endereçamento para sua máquina (máx 64):");
        Long bits = Long.parseLong(sc.nextLine());
    
        System.out.println("\nEscolha o tamanho de uma página em B:");
        Long pageSize = Long.parseLong(sc.nextLine());

        System.out.println("\nDefina o tamanho da sua memória física em B:");
        Long tamanhoMemoriaFisicaB = Long.parseLong(sc.nextLine());
    
        configurarMaquina(bits, pageSize, tamanhoMemoriaFisicaB, null);
    }
    
    public void maquinaSetUp(Long maquinaBits, Long pageSize, Long tamanhoMemoriaFisicaB, String memoriaFisicaAlg) {
        if (tamanhoMemoriaFisicaB == null) {
            tamanhoMemoriaFisicaB = (long) Math.pow(2, maquinaBits);
        }
        configurarMaquina(maquinaBits, pageSize, tamanhoMemoriaFisicaB, memoriaFisicaAlg);
    }
    
    private void configurarMaquina(Long bits, Long pageSize, Long tamanhoMemoriaFisicaB, String memoriaFisicaAlg) {
        montaMemoriaFisica(bits, pageSize, tamanhoMemoriaFisicaB, memoriaFisicaAlg);
        montaMaquina(bits, pageSize);
    }

    private void montaMemoriaFisica(Long bits, Long pageSize, Long tamanhoMemoriaFisicaB, String memoriaFisicaAlg) {
        Long qtdPages = (long) Math.pow(2, bits) / pageSize;
    
        AlgoritmoSubstituicaoI<Long> algoritmoMemoriaFisica = criarAlgoritmoSubstituicao(qtdPages, "Memória Física", memoriaFisicaAlg);
    
        this.memoriaFisica = new MemoriaFisica(bits, pageSize, algoritmoMemoriaFisica);
    }

    private void montaMaquina(Long bits, Long pageSize) {
        this.maquina = new Maquina(bits, pageSize, tlb, memoriaFisica);
    }

    public void tlbSetUp() {
        System.out.println("\nDefina a quantidade de entradas da sua TLB (máx 64):");
        Long qtdEntry = Long.parseLong(sc.nextLine());
    
        configurarTLB(qtdEntry, null);
    }
    
    public void tlbSetUp(Long qtdEntry, String tlbAlg) {
        configurarTLB(qtdEntry, tlbAlg);
    }
    
    private void configurarTLB(Long qtdEntry, String tlbAlg) {
        AlgoritmoSubstituicaoI<TlbEntry> algoritmoTLB = criarAlgoritmoSubstituicao(qtdEntry, "TLB", tlbAlg);
        this.montaTLB(algoritmoTLB);
    }

    private void montaTLB(AlgoritmoSubstituicaoI<TlbEntry> algoritmo) {
        this.tlb = new TLB(algoritmo);
    }

    private <T> AlgoritmoSubstituicaoI<T> criarAlgoritmoSubstituicao(Long qtdEntries, String componente, String algName) {
        if (algName == null) {
            System.out.println("\nSelecionando o algoritmo de substituição para a " + componente + ":");
            System.out.println("[1] FIFO");
            System.out.println("[2] LFU");
            System.out.println("[3] LRU");
            System.out.println("[4] Second Chance");
            System.out.print("Escolha uma opção: ");
            algName = sc.nextLine();
        }

        AlgoritmoSubstituicaoI<T> algoritmo = null;

        switch (algName.toLowerCase()) {
            case "1":
            case "fifo":
                algoritmo = new FIFO<>(qtdEntries);
                break;
            case "2":
            case "lfu":
                algoritmo = new LFU<>(qtdEntries);
                break;
            case "3":
            case "lru":
                algoritmo = new LRU<>(qtdEntries);
                break;
            case "4":
            case "secondchance":
                algoritmo = new SecondChance<>(qtdEntries);
                break;
            default:
                System.out.println("\nOpção inválida para " + componente + ". Tente novamente.");
                return criarAlgoritmoSubstituicao(qtdEntries, componente, null);
        }
        return algoritmo;
    }

    public void imprimeMaquina() {
        System.out.println("\n=================================");
        System.out.println("       MÁQUINA CONFIGURADA!       ");
        System.out.println("=================================\n");

        System.out.println(
                "TLB configurada com o algoritmo " + tlb.nomeAlgoritmo() + " e " + tlb.getQtdEntries() + " entradas.");
        System.out.println("Tamanho da Page Table: " + maquina.pageTableSizeBytes() + "B");
        System.out.println("Tamanho da PTE: " + maquina.getTamanhoPTE() + "B");
        System.out.println("Quantidade de Pages/PageFrames: " + maquina.qtdPages() + "\n");
    }

    public void iniciarSimulacao() throws Interruption {
        String explicacao = "";
        System.out.println(
                "\n🔄 Iniciando a simulação de traduções de endereço!\n"
                        + "a simulação avança mediante sua interação\n"
                        + "=> Qual Endereço Virtual gostaria de traduzir?\n");

        Long traducaoInicial = Long.parseLong(sc.nextLine());
        maquina.iniciarTraducaoDeEndereco(traducaoInicial);

        System.out.println("Atenção: durante o processo, se quiser maiores explicações para etapa, digite '?'");
        System.out.println("Digite '.' para continuar a tradução ou '!' para parar:");
        String option = sc.nextLine();
        while (!option.equals("!") && maquina.getEmOperacao()) {
            switch (option) {
                case ".":
                    explicacao = maquina.getEstado().explicacao();
                    maquina.executarEstadoAtual();
                    break;
                case "?":
                    System.out.println(explicacao);
                    break;
                case "!":
                    return;
                default:
                    System.out.println("\nOpção inválida. Tente novamente.\n");
            }
            System.out.println("Digite '.' para continuar a tradução ou '!' para parar:");
            option = sc.nextLine();
        }
        this.reiniciarTraducao();
    }

    public void terminarSimulacao() {
        System.out.println("\n✅ Simulação finalizada. Até breve!\n");
    }

    private void reiniciarTraducao() throws Interruption {
        System.out.println("\nQual sua próxima ação?\n");
        System.out.println("[1] Traduzir novo endereço");
        System.out.println("[2] Voltar ao menu inicial");
        System.out.println("[3] Sair");
        int option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 1:
                iniciarSimulacao();
                break;
            case 2:
                pagex.menuInicial();
                return;
            case 3:
                System.out.println("\n👋 Obrigado por usar o PageX! Até mais!\n");
                System.exit(0);
                break;
            default:
                System.out.println("\nOpção inválida. Tente novamente.\n");
                reiniciarTraducao();
        }
    }
}
