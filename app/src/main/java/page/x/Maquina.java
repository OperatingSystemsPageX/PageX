package page.x;

import page.x.TLB.TLB;
import page.x.estados.SepararBitsState;
import page.x.estados.TraducaoState;
import page.x.memoriafisica.MemoriaFisica;
import page.x.pagetable.PageTable;

public class Maquina {
    private TraducaoState traducaoState;
    private Long qtdBits;
    private Long tamanhoDaPaginaEmKB;
    private TLB tlb;
    private MemoriaFisica memoriaFisica;

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
    public TraducaoState getEstado() {
        return this.traducaoState;
    }

    public void setMemoriaFisica() {
        this.memoriaFisica = new MemoriaFisica(this.qtdBits, this.tamanhoDaPaginaEmKB);
    }

    public void setTlb(TLB tlb) {
        this.tlb = tlb;
    }
    public Long getQtdBits() {
        return qtdBits;
    }
    public Long getTamanhoDaPaginaEmKB() {
        return tamanhoDaPaginaEmKB;
    }
    public TLB getTlb() {
        return tlb;
    }

    public PageTable getPageTable() {
        return this.memoriaFisica.getPageTable();
    }

    public Long pageTableSizeBytes() {
        return this.memoriaFisica.getPageTable().getTamanho();
    }

    public Long qtdPages() {
        return this.memoriaFisica.getPageTable().getQtdDePaginas();
    }

    public Long getTamanhoPTE() {
        return this.memoriaFisica.getPageTable().getTamanhoPte();
    }
}
