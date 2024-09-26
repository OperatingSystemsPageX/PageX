package page.x;

import page.x.estados.SepararBitsState;
import page.x.estados.TraducaoState;

public class Maquina {
    private TraducaoState traducaoState;
    private Long qtdBits;
    private Long tamanhoDaPaginaEmKB;

    public Maquina (Long qtdBits, Long tamanhoDaPaginaEmKB) {
        this.qtdBits = qtdBits;
        this.traducaoState = new SepararBitsState(this);
        this.tamanhoDaPaginaEmKB = tamanhoDaPaginaEmKB;
    }

    public void setTraducaoState(TraducaoState traducaoState) {
        this.traducaoState = traducaoState;
    }

    public void avancarEstado() {
        this.traducaoState.efetuarOperacao();
    }

    public Long getQtdBits() {
        return qtdBits;
    }

    public Long getTamanhoDaPaginaEmKB() {
        return tamanhoDaPaginaEmKB;
    }
}
