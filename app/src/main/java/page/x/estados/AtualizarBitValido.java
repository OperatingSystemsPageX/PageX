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
        this.toStringState();
        this.pageTableEntry.mapear(true);
        this.toStringMapeamento();
        this.avancaEstado();
    }
    
    @Override
    public void avancaEstado() {
        TraducaoState proximoEstado = new VerificarBitValidoState(maquina, pageTableEntry, enderecoVirtual);
        this.maquina.setTraducaoState(proximoEstado);
    }
    
    private void toStringState() {        
        System.out.println("\n=============================");
        System.out.println("  ATUALIZANDO BIT VÁLIDO ");
        System.out.println("=============================\n");
    }

    private void toStringMapeamento() {        
        System.out.println("Bit de validade atualizado para 'válido'.\n");
    }
}