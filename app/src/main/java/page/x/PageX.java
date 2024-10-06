package page.x;

import page.x.cli.ModoSimulador;
import page.x.interruptions.Interruption;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.Scanner;

@Command(name = "PageX", mixinStandardHelpOptions = true, description = "Simulador de tradução de páginas")
public class PageX {

    @Option(names = "--page-size", description = "Tamanho da página", defaultValue = Option.NULL_VALUE)
    private Long pageSize;

    @Option(names = "--maquina", description = "Número de bits da máquina", defaultValue = Option.NULL_VALUE)
    private Long maquinaBits;

    @Option(names = "--tlb-entry", description = "Número de entradas da TLB", defaultValue = "10")
    private Long tlbEntries;

    @Option(names = "--tlb-alg", description = "Algoritmo da TLB", defaultValue = "lru")
    private String tlbAlg;

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
            modoSimulador.maquinaSetUp(maquinaBits, pageSize);
        }
        modoSimulador.imprimeMaquina();
        modoSimulador.iniciarSimulacao();
        modoSimulador.terminarSimulacao();
    }
}
