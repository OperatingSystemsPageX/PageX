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
        Long tamanhoPagina = maquina.getTamanhoDaPaginaEmBytes();
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
    @Override
    public String explicacao() {
        return "Nesse estado, a partir do endereço virtual fornecido e das informações que temos sobre\n" +
                            "as configurações da sua máquina, é possível que encontremos em qual Página Virtual (VPN)\n" +
                            "está seu endereço. Esse cálculo pode ser feito com valores binários, ou como nesse caso, decimais.\n" +
                            "Para o cálculo com decimais, a partir do tamanho de cada page, obtemos o intervalo do endereçamento desta,\n" +
                            "dessa forma, além de encontrar o número da Page, também obtemos o valor de deslocamento (offset).";
    }

}