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
    private boolean emOperacao;
    private Long qtdBits;
    private Long tamanhoDaPaginaEmBytes;
    private TLB tlb;
    private MemoriaFisica memoriaFisica;
    private InterruptHandler interruptHandler;

    public Maquina (Long qtdBits, Long tamanhoDaPaginaEmBytes, TLB tlb, MemoriaFisica memoriaFisica) {
        this.qtdBits = qtdBits;
        this.tamanhoDaPaginaEmBytes = tamanhoDaPaginaEmBytes;
        this.tlb = tlb;
        this.memoriaFisica = memoriaFisica;
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

    public void iniciarTraducaoDeEndereco(Long enderecoVirtual) throws Interruption {
        this.traducaoState.efetuarOperacao();
        TraducaoState separarBits = new SepararBitsState(this, enderecoVirtual);
        this.traducaoState = separarBits;

    }

    public TraducaoState getEstado() {
        return this.traducaoState;
    }

    public Long getQtdBits() {
        return qtdBits;
    }

    public Long getQtdBitsOffset() {
        return (long) (Math.log(tamanhoDaPaginaEmBytes) / Math.log(2));
    }

    public Long getTamanhoDaPaginaEmBytes() {
        return tamanhoDaPaginaEmBytes;
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

    public void setEmOperacao(boolean emOperacao) {
        this.emOperacao = emOperacao;
    }

    public boolean getEmOperacao() {
        return emOperacao;
    }

}
