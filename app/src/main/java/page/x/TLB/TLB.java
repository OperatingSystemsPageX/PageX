package page.x.TLB;

import page.x.TLB.algoritmos.substituicao.AlgoritmoSubstituicaoI;
import page.x.interruptions.MissInterruption;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TLB {
    private AlgoritmoSubstituicaoI algoritmo;
    private  TlbEntry[] tlbEntries;
    private int quantidadeDeMiss;
    private int qtdEntries;
    private int quantidadeDeHit;

    public TLB(AlgoritmoSubstituicaoI algoritmo, int qtdEntries) {
        this.algoritmo = algoritmo;
        this.quantidadeDeMiss = 0;
        this.quantidadeDeHit = 0;
        this.qtdEntries = qtdEntries;
        this.tlbEntries = new TlbEntry[qtdEntries];
    }

    public int getMiss() {
        return quantidadeDeMiss;
    }

    public int getHit() {
        return quantidadeDeHit;
    }

    public Integer mapearPagina(Integer page) throws MissInterruption {
        try {
            Integer result = this.algoritmo.mapearPagina(page);
            this.quantidadeDeHit++;
            return result;
        } catch (MissInterruption miss) {
            this.quantidadeDeMiss++;
            throw new MissInterruption();
        }
    }
    public void addPaginaMapeada(Integer vpn, Integer pfn) {
        this.algoritmo.addPaginaMapeada(vpn, pfn);
    }

    public String nomeAlgoritmo() {
        return this.algoritmo.nomeToString();
    }

    public int getQtdEntries() {
        return qtdEntries;
    }
}
