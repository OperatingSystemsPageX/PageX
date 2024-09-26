/* Atualizar a TLB:
    - Chama a função do algoritmo de substituição para efetuar as mudanças na TLB
    - Muda o estado para o primeiro de todos(SepararBitsState) e não o invoca.
*/
package page.x.estados;

import page.x.Maquina;

public class AtualizarTLBState implements TraducaoState {
    private Maquina maquina;
    @SuppressWarnings("unused")
    private EnderecoVirtual enderecoVirtual;

    public AtualizarTLBState(Maquina maquina, EnderecoVirtual enderecoVirtual) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
    }
    
    @Override
    public void efetuarOperacao() {
        TraducaoState proximoEstado = new SepararBitsState(maquina);
        maquina.setTraducaoState(proximoEstado);
        // Não executa o próximo estado, permite voltar pro main
    }
}