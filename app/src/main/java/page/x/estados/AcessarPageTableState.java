/* Acessar a page table:
    - Olhar o valor do registrador PTBR, e informar que a page table está em tal posição da memória(por default será no final)
    - Verifica se o PFN já foi alocado por meio do bit de validade(que no nosso contexto, significa estar ou não no HashMap da Page Table)
    - Ir para o estado que verifica o bit valido(será que precisa mesmo ter esse estado?)
*/
package page.x.estados;

import page.x.Maquina;

public class AcessarPageTableState implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;

    public AcessarPageTableState(Maquina maquina, EnderecoVirtual enderecoVirtual) {
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