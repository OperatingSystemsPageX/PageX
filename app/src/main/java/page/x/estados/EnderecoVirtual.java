package page.x.estados;

public class EnderecoVirtual {
    private Long VPN;
    private Long deslocamento;

    public EnderecoVirtual(Long VPN, Long deslocamento) {
        this.VPN = VPN;
        this.deslocamento = deslocamento;
    }

    public Long getVPN() {
        return VPN;
    }

    public Long getOffset() {
        return deslocamento;
    }
}
