package page.x;

import page.x.TLB.TLB;
import page.x.estados.AguardarTraducao;
import page.x.estados.SepararBitsState;
import page.x.estados.TraducaoState;
import page.x.interruptions.InterruptHandler;
import page.x.interruptions.Interruption;
import page.x.memoriafisica.MemoriaFisica;
import page.x.pagetable.PageTable;

public class Maquina {
    private TraducaoState traducaoState;
    private Long qtdBits;
    private Long tamanhoDaPaginaEmKB;
    private TLB tlb;
    private MemoriaFisica memoriaFisica;
    private InterruptHandler interruptHandler;

    public Maquina (Long qtdBits, Long tamanhoDaPaginaEmKB, TLB tlb) {
        this.qtdBits = qtdBits;
        this.tamanhoDaPaginaEmKB = tamanhoDaPaginaEmKB;
        this.tlb = tlb;
        this.traducaoState = new AguardarTraducao(this);
        this.interruptHandler = new InterruptHandler(this);
    }

    public void setTraducaoState(TraducaoState traducaoState) {
        this.traducaoState = traducaoState;
    }

    public void executarEstadoAtual() {
        try {
            this.traducaoState.efetuarOperacao();
        } catch (Interruption interruption) {
            this.interruptHandler.handleInterruption(interruption);
        }
    }

    public void iniciarTraducaoDeEndereco(Long enderecoVirtual) {
        TraducaoState separarBits = new SepararBitsState(this, enderecoVirtual);
        this.traducaoState = separarBits;

    }

    public TraducaoState getEstado() {
        return this.traducaoState;
    }

    public void criarMemoriaFisica() {
        this.memoriaFisica = new MemoriaFisica(this.qtdBits, this.tamanhoDaPaginaEmKB);
    }

    public Long getQtdBits() {
        return qtdBits;
    }

    public Long getTamanhoDaPaginaEmKB() {
        return tamanhoDaPaginaEmKB;
    }

    public Long getTamanhoDaPaginaEmBytes() {
        return this.memoriaFisica.getPageTable().getPageEmBytes();
    }

    public TLB getTlb() {
        return tlb;
    }

    public MemoriaFisica getMemoriaFisica() {
        return memoriaFisica;
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

    public void reset() {
        this.memoriaFisica = new MemoriaFisica(this.qtdBits, this.tamanhoDaPaginaEmKB);
        this.traducaoState = new AguardarTraducao(this);
        this.interruptHandler = new InterruptHandler(this);
        this.tlb.reset();
    }
}
