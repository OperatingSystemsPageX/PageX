package page.x;

import page.x.estados.SepararBitsState;
import page.x.estados.TraducaoState;

public class Maquina {
    private TraducaoState traducaoState;

    public Maquina () {
        this.traducaoState = new SepararBitsState(this);
    }

    public void setTraducaoState(TraducaoState traducaoState) {
        this.traducaoState = traducaoState;
    }

    public void avancarEstado() {
        this.traducaoState.efetuarOperacao();
    }
}
