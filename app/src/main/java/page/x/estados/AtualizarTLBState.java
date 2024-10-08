package page.x.estados;

import page.x.Maquina;
import page.x.TLB.TlbEntry;

public class AtualizarTLBState implements TraducaoState {
    private Maquina maquina;
    private Long VPN, PFN;
    private TlbEntry oldEntry;

    public AtualizarTLBState(Maquina maquina, Long VPN, Long PFN) {
        this.maquina = maquina;
        this.VPN = VPN;
        this.PFN = PFN;
    }
    
    @Override
    public void efetuarOperacao() {
        oldEntry = this.maquina.getTlb().addPaginaMapeada(VPN, PFN);
        this.toStringState();
        this.avancaEstado();
    }
    
    @Override
    public void avancaEstado() {        
        TraducaoState proximoEstado = new AguardarTraducao(maquina);
        this.maquina.setTraducaoState(proximoEstado);
    }

    @Override
    public String explicacao() {
        return "Coloca-se na TLB o mapeamento entre o VPN do endereço que foi traduzido e qual Page Frame \n" +
               "ele está armazenado. Dessa forma, para futuros acessos nessa mesma página, que devido ao princípio\n" +
               "da localidade temporal, provavelmente ocorrerão, haverá um Hit Ratio e o processo será muito mais rápido.";
    }


    private void toStringState() {        
        System.out.println("\n=============================");
        System.out.println("        ATUALIZANDO TLB        ");
        System.out.println("==============================\n");
        if (oldEntry != null)
            System.out.println("O endereço " + oldEntry.getVirtualPageNumber() + " foi expulso e a ");
        System.out.println("TLB foi atualizado com o VPN: " + VPN);
    }
}