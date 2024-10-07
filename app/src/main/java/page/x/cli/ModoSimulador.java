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
        System.out.println("\n===================================");
        System.out.println(" CONFIGURAÇÃO DA MÁQUINA INICIADA");
        System.out.println("===================================\n");
    
        System.out.println("Escolha a quantidade de bits de endereçamento para sua máquina (máx 64):");
        Long bits = Long.parseLong(sc.nextLine());


        System.out.println("\nEscolha o tamanho de uma página (especifique se a unidade é B ou KB):");
        String input = sc.nextLine();
        Long pageSize = validaEntrada(input);

        System.out.println("\nDefina o tamanho da sua memória física em B (especifique se a unidade é B ou KB):");
        String input2 = sc.nextLine();
        Long tamanhoMemoriaFisicaB = validaEntrada(input2);

        verificaEntradas(bits, pageSize, tamanhoMemoriaFisicaB);
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
        verificaEntradas(qtdEntry);
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
        rangeEnderecamento(traducaoInicial);
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

    private Long validaEntrada(String input) {
        Long valorNumerico = 0L;
        input.trim();

        String[] parts = input.split("(?<=\\d)(?=\\D)");

        if (parts.length == 2) {
            valorNumerico = Long.parseLong(parts[0].trim());
            String unidade = parts[1].trim().toUpperCase();

            if (unidade.equals("KB")) {
                valorNumerico *= 1024;
            } else if (!unidade.equals("B")) {
                System.out.println("Unidade inválida. Use 'B' para bytes ou 'KB' para kilobytes.");
            }

        } else {
            System.out.println("Entrada inválida. Certifique-se de passar um valor numérico seguido de 'B' ou 'KB'.");
        }
        return valorNumerico;
    }

    private void verificaEntradas(Long bits, Long pageSize, Long tamanhoMemoriaFisicaB) {
        if (bits > 64 || bits <= 0) {
            System.out.println("Entrada inválida. Certifique-se de passar um valor numérico maior que 0 e menor ou igual 64.");
            maquinaSetUp();
        }
        if (pageSize > tamanhoMemoriaFisicaB) {
            System.out.println("Impossível configurar uma página maior que a memória.");
            maquinaSetUp();
        }
    }

    private void verificaEntradas(Long bits) {
        if (bits > 64 || bits < 0) {
            System.out.println("Entrada inválida. Certifique-se de passar um valor numérico positivo e menor ou igual 64.");
            tlbSetUp();
        }
    }

    private void rangeEnderecamento(Long enderecoVirtual) throws Interruption {
        if (enderecoVirtual > maquina.rangeEnderecosVirtuais()) {
            System.out.println("O endereço está fora do range de endereçamento.\n" +
                    "Tente novamente com um valor até " + maquina.rangeEnderecosVirtuais());
            iniciarSimulacao();
        }
    }
}
