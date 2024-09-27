package page.x.cli;

import page.x.Maquina;
import page.x.TLB.TLB;
import page.x.TLB.algoritmos.substituicao.*;

import java.util.Scanner;

public class ModoSimulador {
    private Scanner sc = new Scanner(System.in);
    private Maquina maquina;
    private TLB tlb;

    public void maquinaSetUp() {
        System.out.println("Escolha a quantidade de bits de endereçamento para sua máquina (no máximo 64):" + "\n");
        int bits = sc.nextInt();
        System.out.println("Escolha o tamanho de uma page em KB:" + "\n");
        int pageSize = sc.nextInt();
        montaMaquina(bits, pageSize);
    }

    public void tlbSetUp() {
        AlgoritmoSubstituicaoI algoritmo = null;

        System.out.println("Defina a quantidade de entradas da sua TLB (no máximo 64):" + "\n");
        int qtdEntry = sc.nextInt();

        System.out.println("Por fim, defina o algoritmo em que sua TLB opera" + "\n");
        System.out.println("[1] FIFO");
        System.out.println("[2] LFU");
        System.out.println("[3] LRU");
        System.out.println("[4] Second Chance");
        int option = sc.nextInt();

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
            System.out.println("Opção inválida. Tente novamente.");
        }

        montaTLB(qtdEntry, algoritmo);
    }

    private void montaTLB(int qtdEntry, AlgoritmoSubstituicaoI algoritmo) {
        this.tlb = new TLB(algoritmo);
    }


    private void montaMaquina(int bits, int pageSize) {
        this.maquina = new Maquina((long) bits, (long) pageSize);
        maquina.setTlb(this.tlb);
        maquina.setMemoriaFisica();
    }
    public void imprimeMaquina() {
        System.out.println("---------------------------" + "\n" +
                            "   MÁQUINA CONFIGURADA!   " + "\n" +
                            "---------------------------" + "\n");

        System.out.println("TLB: executa o algoritmo " + tlb.nomeAlgoritmo() + " e possui " + tlb.getQtdEntries() + " entradas.");
        System.out.println("Page Table: " + maquina.pageTableSizeBytes() + "B");
        System.out.println("Tamanho da PTE: " + maquina.getTamanhoPTE() + "B");
        System.out.println("Quantidade de Pages/PageFrames: " + maquina.qtdPages());
    }


}
