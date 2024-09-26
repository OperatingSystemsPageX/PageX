/* Verificar a TLB:
    - Varre toda a TLB em busca do PFN
    - Se encontrar, avança para o calculo do deslocamento do endereço físico no page frame
    - Se não encontrar, lança uma interrupção(TLB Miss) que vai mudar o estado da Maquina para AcessarPageTableState
*/
package page.x.estados;

import page.x.Maquina;

public class VerificarTLBState  implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;

    public VerificarTLBState(Maquina maquina, EnderecoVirtual enderecoVirtual) {
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