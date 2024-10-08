package page.x.TLB;

import page.x.TLB.algoritmos.substituicao.AlgoritmoSubstituicaoI;
import page.x.interruptions.MissInterruption;

public class TLB {
    private AlgoritmoSubstituicaoI<TlbEntry> algoritmo;

    private int quantidadeDeMiss;

    private int quantidadeDeHit;

    public TLB(AlgoritmoSubstituicaoI<TlbEntry> algoritmo) {
        this.algoritmo = algoritmo;
        this.quantidadeDeMiss = 0;
        this.quantidadeDeHit = 0;
    }

    public int getMiss() {
        return quantidadeDeMiss;
    }

    public int getHit() {
        return quantidadeDeHit;
    }

    public double getHitRatio() {
        double result = ( (double) this.quantidadeDeHit / (this.quantidadeDeHit + this.quantidadeDeMiss) );
        return result * 100;
    }

    public Long mapearPagina(Long page) throws MissInterruption {
        try {
            Long result = this.algoritmo.acessEntry(page).getPageFrameNumber();
            this.quantidadeDeHit++;
            return result;
        } catch (MissInterruption miss) {
            this.quantidadeDeMiss++;
            throw new MissInterruption();
        }
    }

    public TlbEntry addPaginaMapeada(Long vpn, Long pfn) {
        TlbEntry tlbEntry = new TlbEntry(vpn, pfn);
        return this.algoritmo.addEntry(tlbEntry);
    }

    public String nomeAlgoritmo() {
        return this.algoritmo.nomeToString();
    }

    public Long getQtdEntries() {
        return this.algoritmo.getQtdEntries();
    }

}
