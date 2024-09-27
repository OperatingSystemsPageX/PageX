package page.x.TLB;

import page.x.TLB.algoritmos.substituicao.AlgoritmoSubstituicaoI;
import page.x.interruptions.MissInterruption;

public class TLB {
    private AlgoritmoSubstituicaoI algoritmo;

    private int quantidadeDeMiss;

    private int quantidadeDeHit;

    public TLB(AlgoritmoSubstituicaoI algoritmo) {
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

    public Long mapearPagina(Long page) throws MissInterruption {
        try {
            Long result = this.algoritmo.mapearPagina(page);
            this.quantidadeDeHit++;
            return result;
        } catch (MissInterruption miss) {
            this.quantidadeDeMiss++;
            throw new MissInterruption();
        }
    }

    public void addPaginaMapeada(Long vpn, Long pfn) {
        this.algoritmo.addPaginaMapeada(vpn, pfn);
    }

    public String nomeAlgoritmo() {
        return this.algoritmo.nomeToString();
    }

    public int getQtdEntries() {
        return this.algoritmo.getQtdEntries();
    }
}
