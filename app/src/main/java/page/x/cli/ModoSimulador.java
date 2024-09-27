package page.x.cli;

import page.x.Maquina;
import page.x.TLB.TLB;
import page.x.TLB.algoritmos.substituicao.*;
import page.x.estados.AcessarPageTableState;
import page.x.estados.RecuperarVirtualPageDoDisco;
import page.x.interruptions.MissInterruption;
import page.x.interruptions.PageFaultInterruption;

import java.util.Scanner;

public class ModoSimulador {
    private Scanner sc = new Scanner(System.in);
    private Maquina maquina;
    private TLB tlb;

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
        AlgoritmoSubstituicaoI algoritmo = null;

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

        montaTLB(qtdEntry, algoritmo);
    }

    private void montaTLB(int qtdEntry, AlgoritmoSubstituicaoI algoritmo) {
        this.tlb = new TLB(algoritmo);
    }

    private void montaMaquina(int bits, int pageSize) {
        this.maquina = new Maquina((long) bits, (long) pageSize, tlb);
        maquina.criarMemoriaFisica();
    }

    public void imprimeMaquina() {
        System.out.println("\n=================================");
        System.out.println("       MÁQUINA CONFIGURADA!       ");
        System.out.println("=================================\n");

        System.out.println("TLB configurada com o algoritmo " + tlb.nomeAlgoritmo() + " e " + tlb.getQtdEntries() + " entradas.");
        System.out.println("Tamanho da Page Table: " + maquina.pageTableSizeBytes() + "B");
        System.out.println("Tamanho da PTE: " + maquina.getTamanhoPTE() + "B");
        System.out.println("Quantidade de Pages/PageFrames: " + maquina.qtdPages() + "\n");
    }

    public void iniciarSimulacao() {
        System.out.println("\n🔄 Iniciando a simulação de traduções de endereço!\n Ao longo da simulação, você receberá uma pergunta como abaixo\n");
        while (true) {
            try {
                System.out.println("Digite '.' para continuar a tradução ou '!' para parar:");
                String option = sc.nextLine();
                switch (option) {
                    case ".":
                        maquina.executarEstadoAtual();
                        break;
                    case "!":
                        return;
                    default:
                        System.out.println("\nOpção inválida. Tente novamente.\n");
                } 
            } catch (Exception e) {
                if (e instanceof MissInterruption) {
                    System.out.println("TLB Miss detectado!");
                    MissInterruption missInterruption = (MissInterruption) e;
                    maquina.setTraducaoState(new AcessarPageTableState(maquina, missInterruption.getEnderecoVirtual()));
                } else if (e instanceof PageFaultInterruption) {
                    System.out.println("Page Fault detectado na Page Table!");
                    PageFaultInterruption pageFault = (PageFaultInterruption) e;
                    maquina.setTraducaoState(new RecuperarVirtualPageDoDisco(maquina, pageFault.getPageTableEntry(), pageFault.getEnderecoVirtual()));
                }
            }
        }
    }

    public void terminarSimulacao() {
        System.out.println("\n✅ Simulação finalizada. Até breve!\n");
    }
}
