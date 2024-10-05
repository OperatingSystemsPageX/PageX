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

    @Override
    public String explicacao() {
        return "Após o tratamento do Page Fault para a ausência da página em questão na memória, mudamos o bit de validade\n" +
               "(ou de mapeamento) para indicar na PTE que a página já está alocada e pode ser acessada.";
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