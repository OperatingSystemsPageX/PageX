package page.x;

import page.x.cli.ModoSimulador;
import page.x.interruptions.Interruption;

import java.util.Scanner;

public class PageX {
    ModoSimulador modoSimulador = new ModoSimulador(this);
    Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Interruption {
        PageX pageX = new PageX();

// valores default:
        int pageSize = 2;
        int maquinaBits = 16;
        int tlbEntries = 10;
        String tlbAlg = "lru";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--page-size":
                    pageSize = Integer.parseInt(args[++i]);
                    break;
                case "--maquina":
                    maquinaBits = Integer.parseInt(args[++i]);
                    break;
                case "--tlb-entry":
                    tlbEntries = Integer.parseInt(args[++i]);
                    break;
                case "--tlb-alg":
                    tlbAlg = args[++i];
                    break;
                case "--verbose":
                    pageX.menuInicial();
                    break;
                default:
                    System.out.println("Parâmetro não reconhecido" + args[i]);
                    pageX.menuInicial();
                    break;
            }
        }
        pageX.maquinaFlags(pageSize, maquinaBits, tlbEntries, tlbAlg);
    }

    private void maquinaFlags(int pageSize, int maquinaBits, int tlbEntries, String tlbAlg) throws Interruption {
        modoSimulador.montaMaquina(maquinaBits, pageSize);
        modoSimulador.montaTlb(tlbEntries, tlbAlg);
        modoSimulador.iniciarSimulacao();
        modoSimulador.terminarSimulacao();
    }

    public void menuInicial () throws Interruption {
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

        private void iniciarModoSimulador () throws Interruption {
            modoSimulador.maquinaSetUp();
            modoSimulador.tlbSetUp();
            modoSimulador.imprimeMaquina();
            modoSimulador.iniciarSimulacao();
            modoSimulador.terminarSimulacao();
        }
    }