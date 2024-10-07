package page.x.estados;

import page.x.Maquina;
import page.x.interruptions.Interruption;
import page.x.memoriafisica.MemoriaFisica;
import page.x.pagetable.PageTableEntry;

public class RecuperarVirtualPageDoDisco implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;
    private PageTableEntry pageTableEntry;

    public RecuperarVirtualPageDoDisco(Maquina maquina, PageTableEntry pageTableEntry, EnderecoVirtual enderecoVirtual) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
        this.pageTableEntry = pageTableEntry;
    }
    
    @Override
    public void efetuarOperacao() throws Interruption {
        this.toStringState();
        this.logicaRecuperarDisco();
        this.toStringMapeamento();
        this.avancaEstado();
    }
    
    private void logicaRecuperarDisco() throws Interruption {
        MemoriaFisica memoriaFisica = maquina.getMemoriaFisica();
        Long PFN = memoriaFisica.alocarPageFrame();
        pageTableEntry.setPageFrameNumber(PFN);
    }
    
    @Override
    public void avancaEstado() {
        TraducaoState proximoEstado = new AtualizarBitValido(maquina, pageTableEntry, enderecoVirtual);
        this.maquina.setTraducaoState(proximoEstado);
    }

    @Override
    public String explicacao() {
        return "Nesse caso, a página virtual ainda não havia sido trazida para memória, por isso houve um Page Fault.\n" +
               "Agora houve o tratamento e assim a tradução tem continuidade.";
    }

    private void toStringState() {
        System.out.println("\n===============================");
        System.out.println(" RECUPERANDO PÁGINA DO DISCO ");
        System.out.println("===============================\n");
    }
    
    private void toStringMapeamento() {
        System.out.println("Página alocada em Page Frame Number: " + pageTableEntry.getPageFrameNumber() + "\n");
    }

}