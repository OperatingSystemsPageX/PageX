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
    public void efetuarOperacao() throws Exception {
        System.out.println("\n==========================");
        System.out.println("  VERIFICAÇÃO DA TLB  ");
        System.out.println("==========================\n");

        try {
            TLB tlb = maquina.getTlb();
            Long PFN = tlb.mapearPagina(enderecoVirtual.getVPN());
            System.out.println("Página encontrada na TLB! PFN: " + PFN + "\n");

            TraducaoState proximoEstado = new AcessarEnderecoFisicoState(maquina, PFN, enderecoVirtual);
            maquina.setTraducaoState(proximoEstado);
        } catch (Exception e) {
            throw new MissInterruption(enderecoVirtual);
        }
    }
}