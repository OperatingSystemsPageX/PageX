
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
        Long offset = enderecoVirtual.getOffset();
        Long enderecoFisico = (this.PFN * maquina.getTamanhoDaPaginaEmBytes()) + offset;
        this.toStringState(enderecoFisico);
        this.avancaEstado();
    }
    
    @Override
    public void avancaEstado() {
        TraducaoState proximoEstado = new AtualizarTLBState(maquina, enderecoVirtual.getVPN() , this.PFN);
        this.maquina.setTraducaoState(proximoEstado);
    }
    
    private void toStringState(Long enderecoFisico) {        
        System.out.println("\n=============================");
        System.out.println("        ENDEREÇO FÍSICO:        ");
        System.out.println("==============================\n");
        System.out.println(enderecoFisico);
    }
}


