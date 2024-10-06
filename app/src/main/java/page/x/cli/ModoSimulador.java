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
    private AlgoritmoSubstituicaoI<TlbEntry> algoritmo;

    public ModoSimulador(PageX pagex) {
        this.pagex = pagex;
    }

    public void maquinaSetUp() {
        System.out.println("\n===============================");
        System.out.println(" CONFIGURAÇÃO DA MÁQUINA INICIADA");
        System.out.println("===============================\n");

        System.out.println("Escolha a quantidade de bits de endereçamento para sua máquina (máx 64):");
        Long bits = Long.parseLong(sc.nextLine());

        System.out.println("\nEscolha o tamanho de uma página em KB:");
        Long pageSize = Long.parseLong(sc.nextLine());
        
        montaMemoriaFisicaDefault(bits, pageSize);
        montaMaquina(bits, pageSize);
    }

    public void tlbSetUp() {

        System.out.println("\nDefina a quantidade de entradas da sua TLB (máx 64):");
        Long qtdEntry = Long.parseLong(sc.nextLine());

        System.out.println("\nSelecione o algoritmo de substituição da TLB:");
        System.out.println("[1] FIFO");
        System.out.println("[2] LFU");
        System.out.println("[3] LRU");
        System.out.println("[4] Second Chance");
        int option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 1:
                algoritmo = new FIFO<>(qtdEntry);
                break;
            case 2:
                algoritmo = new LFU<>(qtdEntry);
                break;
            case 3:
                algoritmo = new LRU<>(qtdEntry);
                break;
            case 4:
                algoritmo = new SecondChance<>(qtdEntry);
                break;
            default:
                System.out.println("\nOpção inválida. Tente novamente.\n");
                this.tlbSetUp();
                break;
        }

        this.montaTLB(algoritmo);
    }

    private void montaTLB(AlgoritmoSubstituicaoI<TlbEntry> algoritmo) {
        this.tlb = new TLB(algoritmo);
    }

    private void montaMaquina(Long bits, Long pageSize) {
        this.maquina = new Maquina(bits, pageSize, tlb, memoriaFisica);
    }

    private void montaMemoriaFisicaDefault(Long bits, Long pageSize) {
        Long qtdPages = (long) Math.pow(2, bits) / (pageSize * 1024);
        this.memoriaFisica = new MemoriaFisica(bits, pageSize, new FIFO<>(qtdPages));
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
        System.out.println(
                "\n🔄 Iniciando a simulação de traduções de endereço!\n"
                        + "Ao longo da simulação, você receberá uma pergunta como abaixo\n"
                        + "=> Qual Endereço Virtual gostaria de traduzir?\n");

        Long traducaoInicial = Long.parseLong(sc.nextLine());
        maquina.iniciarTraducaoDeEndereco(traducaoInicial);

        System.out.println("Digite '.' para continuar a tradução ou '!' para parar:");
        String option = sc.nextLine();
        while (option != "!" && maquina.getEmOperacao()) {
            switch (option) {
                case ".":
                    maquina.executarEstadoAtual();
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
