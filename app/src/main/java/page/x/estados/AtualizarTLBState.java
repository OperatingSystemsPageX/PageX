
package page.x.estados;

import page.x.Maquina;

public class AtualizarTLBState implements TraducaoState {
    private Maquina maquina;
    private Long VPN, PFN;

    public AtualizarTLBState(Maquina maquina, Long VPN, Long PFN) {
        this.maquina = maquina;
        this.VPN = VPN;
        this.PFN = PFN;
    }
    
    @Override
    public void efetuarOperacao() {

        System.out.println("\n=============================");
        System.out.println("        ATUALIZANDO TLB        ");
        System.out.println("==============================\n");

        System.out.println("seu processo de tradução chegou ao fim!");
        this.maquina.getTlb().addPaginaMapeada(VPN, PFN);
        this.avancaEstado();
    }
    
    @Override
    public void avancaEstado() {        
        TraducaoState proximoEstado = new AguardarTraducao(this.maquina);
        maquina.setTraducaoState(proximoEstado);
    }
}