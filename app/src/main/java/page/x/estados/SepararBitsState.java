/* Separador de bits:
    - pega os bits da maquina e realiza as devidas operações para dividir em offset e pfn
    - cria o objeto EnderecoVirtual
    - Avança para o estado de verificação da TLB
*/
package page.x.estados;

import page.x.Maquina;

public class SepararBitsState  implements TraducaoState {
    private Maquina maquina;

    public SepararBitsState(Maquina maquina) {
        this.maquina = maquina;
    }
    
    @Override
    public void efetuarOperacao() {
        EnderecoVirtual enderecoVirtual = new EnderecoVirtual(0, 0);
        TraducaoState proximoEstado = new VerificarTLBState(maquina, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
        maquina.avancarEstado();
    }
}