/* Acessar até que seja solicitada uma tradução:
    - O próximo estado após esse, é o de Separação de Bits
*/
package page.x.estados;

import page.x.Maquina;

public class AguardarTraducao implements TraducaoState {
    private Maquina maquina;

    public AguardarTraducao(Maquina maquina) {
        this.maquina = maquina;
    }
    
    @Override
    public void efetuarOperacao() {
        System.out.println("\n=========================");
        System.out.println("   TRADUÇÃO DE ENDEREÇO   ");
        System.out.println("=========================\n");

        System.out.print("Digite o endereço virtual que deseja traduzir (em formato numérico decimal): ");
        
        System.out.println("\nEndereço recebido com sucesso!\n");
        
    }
    
    @Override
    public void avancaEstado() {}
}
