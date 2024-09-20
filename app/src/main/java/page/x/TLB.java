package page.x;

import page.x.algoritmos.substituicao.AlgoritmoSubstituicaoI;
import page.x.interruptions.MissInterruption;
import page.x.utils.Pair;

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

    public Integer mapearPagina(Integer page) throws Exception {
        try {
            this.quantidadeDeHit++;
            return this.algoritmo.mapearPagina(page);
        } catch (MissInterruption miss) {
            this.quantidadeDeMiss++;
            throw new MissInterruption();
        }
    }

    public void addPaginaMapeada(Pair<Integer, Integer> paginaMapina) {
        this.algoritmo.addPaginaMapeada(paginaMapina);
    }
}
