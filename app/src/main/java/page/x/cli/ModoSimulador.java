package page.x.cli;

import page.x.Maquina;
import page.x.PageX;
import page.x.TLB.TLB;
import page.x.TLB.algoritmos.substituicao.*;
import page.x.interruptions.Interruption;

import java.util.Scanner;

public class ModoSimulador {
    private PageX pagex;
    private Scanner sc = new Scanner(System.in);
    private Maquina maquina;
    private TLB tlb;
    private AlgoritmoSubstituicaoI algoritmo;

    public ModoSimulador(PageX pagex) {
        this.pagex = pagex;
    }

    public void maquinaSetUp() {
        System.out.println("\n===============================");
        System.out.println(" CONFIGURAÇÃO DA MÁQUINA INICIADA");
        System.out.println("===============================\n");

        System.out.println("Escolha a quantidade de bits de endereçamento para sua máquina (máx 64):");
        int bits = Integer.parseInt(sc.nextLine());

        System.out.println("\nEscolha o tamanho de uma página em KB:");
        int pageSize = Integer.parseInt(sc.nextLine());

        montaMaquina(bits, pageSize);
    }

    public void tlbSetUp() {

        System.out.println("\nDefina a quantidade de entradas da sua TLB (máx 64):");
        int qtdEntry = Integer.parseInt(sc.nextLine());

        System.out.println("\nSelecione o algoritmo de substituição da TLB:");
        System.out.println("[1] FIFO");
        System.out.println("[2] LFU");
        System.out.println("[3] LRU");
        System.out.println("[4] Second Chance");
        int option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 1:
                algoritmo = new FIFO(qtdEntry);
                break;
            case 2:
                algoritmo = new LFU(qtdEntry);
                break;
            case 3:
                algoritmo = new LRU(qtdEntry);
                break;
            case 4:
                algoritmo = new SecondChance(qtdEntry);
                break;
            default:
                System.out.println("\nOpção inválida. Tente novamente.\n");
        }

        this.montaTLB(algoritmo);
    }

    public void montaTlb(int tlbEntries, String tlbAlg) {
        switch (tlbAlg) {
            case "fifo":
                algoritmo = new FIFO(tlbEntries);
                break;
            case "lfu":
                algoritmo = new LFU(tlbEntries);
                break;
            case "lru":
                algoritmo = new LRU(tlbEntries);
                break;
            case "secondchance":
                algoritmo = new SecondChance(tlbEntries);
                break;
        }
        this.montaTLB(algoritmo);
    }

    private void montaTLB(AlgoritmoSubstituicaoI algoritmo) {
        this.tlb = new TLB(algoritmo);
    }

    public void montaMaquina(int bits, int pageSize) {
        this.maquina = new Maquina((long) bits, (long) pageSize, tlb);
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
        while (option != "!" && maquina.getEmOperacao()) {
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
