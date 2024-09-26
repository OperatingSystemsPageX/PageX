/* Verificar a O Bit Valido:
    - Se for 1, acessa o endereço físico
    - Se for 0, lança uma interrupção(Page Fault) que muda o estado da Maquina para RecuperarVirtualPageDoDisco
*/
package page.x.estados;

import page.x.Maquina;

public class VerificarBitValidoState  implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;

    public VerificarBitValidoState(Maquina maquina, EnderecoVirtual enderecoVirtual) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
    }
    
    @Override
    public void efetuarOperacao() {
        TraducaoState proximoEstado = new AcessarEnderecoFisicoState(maquina, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
        maquina.avancarEstado();
    }
}