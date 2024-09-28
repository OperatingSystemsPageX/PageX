package page.x.estados;

import page.x.Maquina;

public class SepararBitsState implements TraducaoState {
    private Maquina maquina;
    private Long enderecoVirtualCompleto;
    private EnderecoVirtual enderecoVirtual;

    public SepararBitsState(Maquina maquina, Long enderecoVirtualCompleto) {
        this.maquina = maquina;
        this.enderecoVirtualCompleto = enderecoVirtualCompleto;
    }
    
    @Override
    public void efetuarOperacao() {
        this.enderecoVirtual = criarEnderecoVirtual(enderecoVirtualCompleto);
        this.toStringState();
        this.avancaEstado();
    }

    private EnderecoVirtual criarEnderecoVirtual(Long enderecoVirtualCompleto) {
        Long tamanhoPagina = maquina.getTamanhoDaPaginaEmKB() * 1024L;
        Long VPN = enderecoVirtualCompleto / tamanhoPagina;
        Long offset = enderecoVirtualCompleto % tamanhoPagina;
        return new EnderecoVirtual(VPN, offset);
    }
    
    @Override
    public void avancaEstado() {
        TraducaoState proximoEstado = new VerificarTLBState(maquina, enderecoVirtual);
        this.maquina.setTraducaoState(proximoEstado);
    }
    
    private void toStringState() {
        System.out.println("\n=========================");
        System.out.println(" SEPARAÇÃO DOS BITS ");
        System.out.println("=========================\n");
        System.out.println("Endereço Virtual Completo: " + enderecoVirtualCompleto);
        System.out.println("VPN: " + enderecoVirtual.getVPN());
        System.out.println("Offset: " + enderecoVirtual.getOffset());
    }
    
}