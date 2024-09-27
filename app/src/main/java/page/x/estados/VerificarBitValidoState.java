package page.x.estados;

import page.x.Maquina;
import page.x.interruptions.PageFaultInterruption;
import page.x.pagetable.PageTableEntry;

public class VerificarBitValidoState implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;
    private PageTableEntry pageTableEntry;

    public VerificarBitValidoState(Maquina maquina, PageTableEntry pageTableEntry, EnderecoVirtual enderecoVirtual) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
        this.pageTableEntry = pageTableEntry;
    }
    
    @Override
    public void efetuarOperacao() throws Exception {
        System.out.println("\n==============================");
        System.out.println("  VERIFICAÇÃO DO BIT VÁLIDO  ");
        System.out.println("==============================\n");

        verificarBitValido(pageTableEntry);

        System.out.println("Página válida! Acessando o endereço físico...\n");

        TraducaoState proximoEstado = new AcessarEnderecoFisicoState(maquina, pageTableEntry.getPageFrameNumber(), enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
    }

    public void verificarBitValido(PageTableEntry pageTableEntry) throws PageFaultInterruption {
        if (!pageTableEntry.estaMapeada()) {
            throw new PageFaultInterruption(pageTableEntry, enderecoVirtual);
        }
    }
}