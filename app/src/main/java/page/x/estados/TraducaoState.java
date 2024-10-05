package page.x.estados;

import page.x.interruptions.Interruption;

public interface TraducaoState {
    public void efetuarOperacao() throws Interruption;

    public void avancaEstado();

    public String explicacao();
}
