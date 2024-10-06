
package page.x.estados;

import page.x.Maquina;
import page.x.memoriafisica.MemoriaFisica;

public class AcessarEnderecoFisicoState implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;
    private Long PFN;
    private boolean tlbTraducao;

    public AcessarEnderecoFisicoState(Maquina maquina, Long PFN, EnderecoVirtual enderecoVirtual, boolean tlbTraducao) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
        this.PFN = PFN;
        this.tlbTraducao = tlbTraducao;
    }
    
    @Override
    public void efetuarOperacao() {
        Long offset = enderecoVirtual.getOffset();
        Long enderecoFisico = (this.PFN * maquina.getTamanhoDaPaginaEmBytes()) + offset;
        this.logicaAcessarEndereco(maquina.getMemoriaFisica(), PFN, offset);
        this.toStringState(enderecoFisico);
        this.avancaEstado();
    }
    
    @Override
    public void avancaEstado() {
        TraducaoState proximoEstado = new AtualizarTLBState(maquina, enderecoVirtual.getVPN() , this.PFN);
        if (tlbTraducao) {
            proximoEstado = new AguardarTraducao(maquina);
        }
        this.maquina.setTraducaoState(proximoEstado);
    }

    private void logicaAcessarEndereco(MemoriaFisica memoriaFisica, Long PFN, Long offset) {
        memoriaFisica.acessarEndereco(PFN, offset);
    }
    
    private void toStringState(Long enderecoFisico) {        
        System.out.println("\n=============================");
        System.out.println("        ENDEREÇO FÍSICO:        ");
        System.out.println("==============================\n");
        System.out.println(enderecoFisico);
    }
}


