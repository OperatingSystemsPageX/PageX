/* Acessar até que seja solicitada uma tradução:
    - O próximo estado após esse, é o de Separação de Bits
*/
package page.x.estados;

import page.x.Maquina;

public class AguardarTraducao implements TraducaoState {
    
    private Maquina maquina;

    public AguardarTraducao(Maquina maquina) {
        this.maquina = maquina;
        this.maquina.setEmOperacao(false);
    }

    @Override
    public void efetuarOperacao() {
        this.maquina.setEmOperacao(true);
    }
    
    @Override
    public void avancaEstado() {}
}
