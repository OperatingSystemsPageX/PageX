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
    private String pageSize;

    @Option(names = "--maquina", description = "Número de bits da máquina", defaultValue = Option.NULL_VALUE)
    private Long maquinaBits;

    @Option(names = "--tlb-entry", description = "Número de entradas da TLB", defaultValue = Option.NULL_VALUE)
    private Long tlbEntries;

    @Option(names = "--tlb-alg", description = "Algoritmo de substituição da TLB", defaultValue = Option.NULL_VALUE)
    private String tlbAlg;

    @Option(names = "--memoria-fisica", description = "Tamanho da memória física", defaultValue = Option.NULL_VALUE)
    private String tamanhoMemoriaFisica;

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

        iniciarModoSimulador();
    }

    private void iniciarModoSimulador() throws Interruption {
        if (maquinaBits == null || pageSize == null) {
            modoSimulador.tlbMaquina();
            modoSimulador.maquinaSetUp();
        } else {
            if (tlbAlg != null && tlbEntries != null)
                modoSimulador.tlbSetUp(tlbEntries, tlbAlg);
            modoSimulador.maquinaSetUp(maquinaBits, pageSize, tamanhoMemoriaFisica, memoriaFisicaAlg);
        }
        modoSimulador.imprimeMaquina();
        modoSimulador.iniciarSimulacao();
        modoSimulador.terminarSimulacao();
    }
}
