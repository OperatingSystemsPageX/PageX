package page.x.estados;

import page.x.Maquina;
import page.x.TLB.TLB;
import page.x.interruptions.MissInterruption;

public class VerificarTLBState implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;

    public VerificarTLBState(Maquina maquina, EnderecoVirtual enderecoVirtual) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
    }
    
    @Override
    public void efetuarOperacao() throws MissInterruption {
        this.toStringState();
        TLB tlb = maquina.getTlb();
        Long PFN = tlb.mapearPagina(enderecoVirtual.getVPN());
        this.toStringHit(PFN);
        this.avancaEstadoHit(PFN);
    }
    
    @Override
    public void avancaEstado() {
        TraducaoState proximoEstado = new AcessarPageTableState(maquina, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
    }
    
    private void avancaEstadoHit(Long PFN) {
        TraducaoState proximoEstado = new AcessarEnderecoFisicoState(maquina, PFN, enderecoVirtual, true);
        maquina.setTraducaoState(proximoEstado);
    }
    
    private void toStringState() {
        System.out.println("\n==========================");
        System.out.println("  VERIFICAÇÃO DA TLB  ");
        System.out.println("==========================\n");
    }

    private void toStringHit(Long PFN) {
        System.out.println("Página encontrada na TLB! PFN: " + PFN + "\n");
        System.out.println("Hit Ratio Atual: " + maquina.getTlb().getHitRatio() + "%");
    }

    @Override
    public String explicacao() {
        return "A TLB é um registrador cache da sua máquina que opera baseado em localidade temporal.\n" +
               "Por isso, a primeira consulta para uma tradução é nesse pedaço do hardware, pois agiliza\n" +
               "consideravelmente o processo. Se o VPN do nosso endereço estiver mapeado para um ID de Page Frame\n" +
               "nessa estrutura de dados, obtemos um Hit, caso contrário, um Miss (e ai seguimos todo fluxo de tradução).";
    }


}