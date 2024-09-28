package page.x;

import page.x.cli.ModoAprendizado;
import page.x.cli.ModoSimulador;
import page.x.interruptions.Interruption;

import java.util.Scanner;

public class PageX {
    public static void main(String[] args) throws Interruption {
        PageX pageX = new PageX();
        
        System.out.println("=======================================" + "\n" +
                           "     🌟 Olá! Bem vindo ao PageX! 🌟     " + "\n" +
                           "=======================================\n");
        
        pageX.menuInicial();
    }

    private ModoAprendizado modoAprendizado;
    private ModoSimulador modoSimulador = new ModoSimulador(this);
    private Scanner sc = new Scanner(System.in);

    public void menuInicial() throws Interruption {
        System.out.println("Selecione uma das opções abaixo:\n");
        System.out.println("[1] 🔄 Simular tradução");
        System.out.println("[2] 📘 Aprender sobre trade-offs");
        System.out.println("[3] ❌ Sair");

        System.out.print("\nEscolha uma opção: ");
        int option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 1:
                iniciarModoSimulador();
                break;
            case 2:
                System.out.println("\n📖 Modo aprendizado em breve...\n");
                menuInicial();
                break;
            case 3:
                System.out.println("\n👋 Obrigado por usar o PageX! Até mais!\n");
                break;
            default:
                System.out.println("\nOpção inválida. Tente novamente.\n");
                menuInicial();
        }
    }

    private void iniciarModoSimulador() throws Interruption {
        modoSimulador.tlbSetUp();
        modoSimulador.maquinaSetUp();
        modoSimulador.imprimeMaquina();
        modoSimulador.iniciarSimulacao();
        modoSimulador.terminarSimulacao();
    }
}