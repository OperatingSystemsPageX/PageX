
package page.x.estados;

import page.x.Maquina;
import page.x.memoriafisica.MemoriaFisica;

public class AcessarEnderecoFisicoState implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;
    private Long PFN;
    private boolean tlbTraducao;
    public static final String RESET = "\u001B[0m";   
    public static final String RED = "\u001B[31m";    
    public static final String GREEN = "\u001B[32m";  

    public AcessarEnderecoFisicoState(Maquina maquina, Long PFN, EnderecoVirtual enderecoVirtual, boolean tlbTraducao) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
        this.PFN = PFN;
        this.tlbTraducao = tlbTraducao;
    }
    
    @Override
    public void efetuarOperacao() {
        Long offset = enderecoVirtual.getOffset();
        Long enderecoFisico = (this.PFN * maquina.getTamanhoDaPaginaEmBytes()) + offset;
        this.logicaAcessarEndereco(maquina.getMemoriaFisica(), PFN, offset);
        this.toStringState(enderecoFisico);
        this.avancaEstado();
    }
    
    @Override
    public void avancaEstado() {
        TraducaoState proximoEstado = new AtualizarTLBState(maquina, enderecoVirtual.getVPN() , this.PFN);
        if (tlbTraducao || maquina.getTlb() == null) {
            proximoEstado = new AguardarTraducao(maquina);
        }
        this.maquina.setTraducaoState(proximoEstado);
    }

    @Override
    public String explicacao() {
        return "Soma-se o valor do offset (deslocamento) ao endereço de início do Page Frame em que nosso\n" +
        "endereço está. Essa soma resulta no endereço físico.";
    }

    private void logicaAcessarEndereco(MemoriaFisica memoriaFisica, Long PFN, Long offset) {
        memoriaFisica.acessarEndereco(PFN, offset);
    }
    
    private void toStringState(Long enderecoFisico) {
        String enderecoFisicoString = Long.toUnsignedString(enderecoFisico, 2);
        String offset = enderecoFisicoString.substring(enderecoFisicoString.length() - maquina.getQtdBitsOffset().intValue());        
        System.out.println("\n=============================");
        System.out.println("        ENDEREÇO FÍSICO:        ");
        System.out.println("==============================\n");
        System.out.println("Endereço Físico Traduzido: " + Long.toUnsignedString(enderecoFisico, 10));
        System.out.print("Representação em binário: ");
        System.out.print(GREEN + Long.toBinaryString(PFN) + RESET);
        System.out.println(RED + offset + RESET);
        System.out.println("PageFrameNumber (Verde): " + PFN);
        System.out.println("Offset (Vermelho): " + enderecoVirtual.getOffset());
    }
}


