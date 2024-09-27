package page.x.estados;

import page.x.Maquina;

public class SepararBitsState implements TraducaoState {
    private Maquina maquina;
    private Long enderecoVirtualCompleto;

    public SepararBitsState(Maquina maquina, Long enderecoVirtualCompleto) {
        this.maquina = maquina;
        this.enderecoVirtualCompleto = enderecoVirtualCompleto;
    }
    
    @Override
    public void efetuarOperacao() {
        EnderecoVirtual enderecoVirtual = criarEnderecoVirtual(enderecoVirtualCompleto);
        TraducaoState proximoEstado = new VerificarTLBState(maquina, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
    }

    private EnderecoVirtual criarEnderecoVirtual(Long enderecoVirtualCompleto) {
        Long tamanhoPagina = maquina.getTamanhoDaPaginaEmKB() * 1024L;
        Long VPN = enderecoVirtualCompleto / tamanhoPagina;
        Long offset = enderecoVirtualCompleto % tamanhoPagina;
        
        System.out.println("\n=========================");
        System.out.println(" SEPARAÇÃO DOS BITS ");
        System.out.println("=========================\n");
        System.out.println("Endereço Virtual Completo: " + enderecoVirtualCompleto);
        System.out.println("VPN: " + VPN);
        System.out.println("Offset: " + offset);
        
        return new EnderecoVirtual(VPN, offset);
    }
}