/* Acessar o endereço físico correspondente ao endereço virtual:
    - Encontrar o endereço do page frame
    - Somar o deslocamento
    - Atualizar a TLB com a virtual page e o page frame
*/
package page.x.estados;

import page.x.Maquina;

public class AcessarEnderecoFisicoState implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;

    public AcessarEnderecoFisicoState(Maquina maquina, EnderecoVirtual enderecoVirtual) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
    }
    
    @Override
    public void efetuarOperacao() {
        TraducaoState proximoEstado = new AtualizarTLBState(maquina, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
        maquina.avancarEstado();
    }
}
