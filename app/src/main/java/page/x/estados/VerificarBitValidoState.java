package page.x.estados;

import page.x.Maquina;
import page.x.interruptions.Interruption;
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
    public void efetuarOperacao() throws Interruption {
        this.toStringState();
        this.verificarBitValido(pageTableEntry);
    }
    
    private void verificarBitValido(PageTableEntry pageTableEntry) throws PageFaultInterruption {
        if (!pageTableEntry.estaMapeada()) {
            throw new PageFaultInterruption();
        } else {
            this.avancaEstadoValido();
        }
    }
    
    private void avancaEstadoValido() {
        System.out.println("Página válida! Acessando o endereço físico...\n");
        TraducaoState proximoEstado = new AcessarEnderecoFisicoState(maquina, pageTableEntry.getPageFrameNumber(), enderecoVirtual, false);
        this.maquina.setTraducaoState(proximoEstado);
    }
    
    @Override
    public void avancaEstado() {
        TraducaoState proximoEstado = new RecuperarVirtualPageDoDisco(maquina, pageTableEntry, enderecoVirtual);
        this.maquina.setTraducaoState(proximoEstado);
    }

    @Override
    public String explicacao() {
        return "Na Page Table Entry, além do ID do Page Frame, vamos encontrar alguns outros bits de utilidade,\n" +
               "dentre eles, o 'mapped bit', que indica se a página que buscamos já foi mapeada ou se houve um page fault.\n";
    }

    private void toStringState() {
        System.out.println("\n==============================");
        System.out.println("  VERIFICAÇÃO DO BIT VÁLIDO  ");
        System.out.println("==============================\n");
    }

}