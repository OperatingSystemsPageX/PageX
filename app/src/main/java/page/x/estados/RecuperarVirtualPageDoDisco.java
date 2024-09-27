package page.x.estados;

import page.x.Maquina;
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
    public void efetuarOperacao() throws Exception {
        System.out.println("\n===============================");
        System.out.println(" RECUPERANDO PÁGINA DO DISCO ");
        System.out.println("===============================\n");

        MemoriaFisica memoriaFisica = maquina.getMemoriaFisica();
        Long PFN = memoriaFisica.alocarPageFrame();
        
        System.out.println("Página alocada em Page Frame Number: " + PFN + "\n");

        pageTableEntry.setPageFrameNumber(PFN);

        TraducaoState proximoEstado = new AtualizarBitValido(maquina, pageTableEntry, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
    }
}