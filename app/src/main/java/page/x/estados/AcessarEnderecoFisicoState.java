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
    private Long PFN;

    public AcessarEnderecoFisicoState(Maquina maquina, Long PFN, EnderecoVirtual enderecoVirtual) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
        this.PFN = PFN;
    }
    
    @Override
    public void efetuarOperacao() {
        System.out.println("Passei pelo acesso ao endereço fisico TLB");
        TraducaoState proximoEstado = new AtualizarTLBState(maquina, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
    }
}
