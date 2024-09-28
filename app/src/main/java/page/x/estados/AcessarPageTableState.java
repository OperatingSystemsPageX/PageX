package page.x.estados;

import page.x.Maquina;
import page.x.pagetable.PageTable;
import page.x.pagetable.PageTableEntry;

public class AcessarPageTableState implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;
    private PageTableEntry pageTableEntry;

    public AcessarPageTableState(Maquina maquina, EnderecoVirtual enderecoVirtual) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
    }
    
    @Override
    public void efetuarOperacao() {
        System.out.println("\n============================");
        System.out.println("  ACESSO À PAGE TABLE ");
        System.out.println("============================\n");

        System.out.println("Registrador PTBR aponta para o final da memória.\n");

        PageTable pageTable = maquina.getPageTable();
        this.pageTableEntry = pageTable.mapearPagina(enderecoVirtual.getVPN());

        System.out.println("Verificando se a página já foi alocada...\n");
        this.avancaEstado();
    }
    
    @Override
    public void avancaEstado() {
        TraducaoState proximoEstado = new VerificarBitValidoState(maquina, pageTableEntry, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
        
    }
}