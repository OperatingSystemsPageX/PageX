
package page.x.estados;

import page.x.Maquina;

public class AtualizarTLBState implements TraducaoState {
    private Maquina maquina;
    private Long vpn, PFN;

    public AtualizarTLBState(Maquina maquina, Long vpn, Long PFN) {
        this.maquina = maquina;
        this.vpn = vpn;
        this.PFN = PFN;
    }
    
    @Override
    public void efetuarOperacao() {

        System.out.println("\n=============================");
        System.out.println("        ATUALIZANDO TLB        ");
        System.out.println("==============================\n");

        System.out.println("seu processo de tradução chegou ao fim!");
        this.maquina.getTlb().addPaginaMapeada(vpn, PFN);
        TraducaoState proximoEstado = new AguardarTraducao(this.maquina);
        maquina.setTraducaoState(proximoEstado);
    }
}