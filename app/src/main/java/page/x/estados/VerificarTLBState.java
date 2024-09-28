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
        System.out.println("\n==========================");
        System.out.println("  VERIFICAÇÃO DA TLB  ");
        System.out.println("==========================\n");
        // Se não achar, lança Exception e o restante do codigo nao é executado
        TLB tlb = maquina.getTlb();
        Long PFN = tlb.mapearPagina(enderecoVirtual.getVPN());

        System.out.println("Página encontrada na TLB! PFN: " + PFN + "\n");
        this.avancaEstadoHit(PFN);
    }
    
    @Override
    public void avancaEstado() {
        TraducaoState proximoEstado = new AcessarPageTableState(maquina, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
    }

    private void avancaEstadoHit(Long PFN) {
        TraducaoState proximoEstado = new AcessarEnderecoFisicoState(maquina, PFN, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
    }
}