/* Acessar até que seja solicitada uma tradução:
    - O próximo estado após esse, é o de Separação de Bits
*/
package page.x.estados;

import java.util.Scanner;

import page.x.Maquina;

public class AguardarTraducao implements TraducaoState {
    private Scanner sc = new Scanner(System.in);
    private Maquina maquina;

    public AguardarTraducao(Maquina maquina) {
        this.maquina = maquina;
    }
    
    @Override
    public void efetuarOperacao() throws Exception {
        System.out.println("\n=========================");
        System.out.println("   TRADUÇÃO DE ENDEREÇO   ");
        System.out.println("=========================\n");

        System.out.print("Digite o endereço virtual que deseja traduzir (em formato numérico decimal): ");
        Long enderecoVirtualCompleto = Long.parseLong(sc.nextLine());
        
        System.out.println("\nEndereço recebido com sucesso!\n");
        
        TraducaoState proximoEstado = new SepararBitsState(maquina, enderecoVirtualCompleto);
        maquina.setTraducaoState(proximoEstado);
        maquina.executarEstadoAtual();
    }
}
