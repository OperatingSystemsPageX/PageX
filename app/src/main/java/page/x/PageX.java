package page.x;

import page.x.cli.ModoSimulador;
import page.x.interruptions.Interruption;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.Scanner;

@Command(name = "PageX", mixinStandardHelpOptions = true, description = "Simulador de tradução de páginas")
public class PageX {

    @Option(names = "--page-size", description = "Tamanho da página em Bytes", defaultValue = Option.NULL_VALUE)
    private Long pageSize;

    @Option(names = "--maquina", description = "Número de bits da máquina", defaultValue = Option.NULL_VALUE)
    private Long maquinaBits;

    @Option(names = "--tlb-entry", description = "Número de entradas da TLB", defaultValue = "10")
    private Long tlbEntries;

    @Option(names = "--tlb-alg", description = "Algoritmo de substituição da TLB", defaultValue = "lru")
    private String tlbAlg;

    @Option(names = "--memoria-fisica", description = "Tamanho da memória física em B", defaultValue = Option.NULL_VALUE)
    private Long tamanhoMemoriaFisicaB;

    @Option(names = "--memoria-alg", description = "Algoritmo de substituição de páginas da Memória Fisica", defaultValue = "fifo")
    private String memoriaFisicaAlg;

    ModoSimulador modoSimulador = new ModoSimulador(this);
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Interruption {
        PageX pageX = new PageX();
        CommandLine.populateCommand(pageX, args);
        pageX.menuInicial();
    }

    public void menuInicial() throws Interruption {
        System.out.println( "======================================="  + "\n" +
                            "     🌟 Olá! Bem vindo ao PageX! 🌟     " + "\n" +
                            "=======================================\n");

        System.out.println("Selecione uma das opções abaixo:\n");
        System.out.println("[1] 🔄 Simular tradução");
        System.out.println("[2] ❌ Sair");

        System.out.print("\nEscolha uma opção: ");
        int option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 1:
                iniciarModoSimulador();
                break;
            case 2:
                System.out.println("\n👋 Obrigado por usar o PageX! Até mais!\n");
                break;
            default:
                System.out.println("\nOpção inválida. Tente novamente.\n");
                menuInicial();
        }
    }

    private void iniciarModoSimulador() throws Interruption {
        if (maquinaBits == null || pageSize == null) {
            modoSimulador.tlbSetUp();
            modoSimulador.maquinaSetUp();
        } else {
            modoSimulador.tlbSetUp(tlbEntries, tlbAlg);
            modoSimulador.maquinaSetUp(maquinaBits, pageSize, tamanhoMemoriaFisicaB, memoriaFisicaAlg);
        }
        modoSimulador.imprimeMaquina();
        modoSimulador.iniciarSimulacao();
        modoSimulador.terminarSimulacao();
    }
}
