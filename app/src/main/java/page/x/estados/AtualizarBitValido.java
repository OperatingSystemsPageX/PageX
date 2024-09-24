/* Atualizar o Bit Valido:
    - Muda o bit de validade na page table(é realmente necessário?)
    - Muda o estado para VerificarBitValidoState
*/
package page.x.estados;

import page.x.Maquina;

public class AtualizarBitValido implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;

    public AtualizarBitValido(Maquina maquina, EnderecoVirtual enderecoVirtual) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
    }
    
    @Override
    public void efetuarOperacao() {
        TraducaoState proximoEstado = new VerificarBitValidoState(maquina, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
        maquina.avancarEstado();
    }
}