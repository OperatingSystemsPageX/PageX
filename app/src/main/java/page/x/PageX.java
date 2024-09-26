package page.x;

import page.x.cli.ModoAprendizado;
import page.x.cli.ModoSimulador;

import java.util.Scanner;

public class PageX {

    private static ModoAprendizado modoAprendizado;
    private static ModoSimulador modoSimulador = new ModoSimulador();
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("---------------------------" + "\n" +
                            " Olá! Bem vindo ao PageX! " + "\n" +
                            "---------------------------" + "\n");
        menuInicial();
    }

    public static void menuInicial() {
        System.out.println("[1] Simular tradução");
        System.out.println("[2] Aprender sobre trade-offs");
        System.out.println("[3] Sair");

        System.out.print("\n" + "Escolha uma opção:" + "\n");
        int option = sc.nextInt();


        switch (option) {
            case 1:
                iniciarModoSimulador();
                break;
            case 2:
                System.out.println("calma que ja chega");
                menuInicial();
                break;
            case 3:
                System.out.println("Até mais!");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                menuInicial();
        }

    }

    private static void iniciarModoSimulador() {
        modoSimulador.maquinaSetUp();
        modoSimulador.tlbSetUp();
        modoSimulador.imprimeMaquina();
    }


}
