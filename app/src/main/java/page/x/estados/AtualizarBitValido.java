package page.x.estados;

import page.x.Maquina;
import page.x.pagetable.PageTableEntry;

public class AtualizarBitValido implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;
    private PageTableEntry pageTableEntry;

    public AtualizarBitValido(Maquina maquina, PageTableEntry pageTableEntry, EnderecoVirtual enderecoVirtual) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
        this.pageTableEntry = pageTableEntry;
    }
    
    @Override
    public void efetuarOperacao() {
        System.out.println("\n=============================");
        System.out.println("  ATUALIZANDO BIT VÁLIDO ");
        System.out.println("=============================\n");

        pageTableEntry.mapear(true);
        System.out.println("Bit de validade atualizado para 'válido'.\n");
        this.avancaEstado();
    }
    
    @Override
    public void avancaEstado() {
        TraducaoState proximoEstado = new VerificarBitValidoState(maquina, pageTableEntry, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
    }
}