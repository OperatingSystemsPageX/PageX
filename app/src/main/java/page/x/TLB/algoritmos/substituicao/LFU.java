package page.x.TLB.algoritmos.substituicao;

import page.x.interruptions.MissInterruption;

import page.x.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class LFU<T> implements AlgoritmoSubstituicaoI<T> {
    
    private Long quantidadeEntries;

    private List<Pair<Integer, T>> entries;

    public LFU(Long quantidadeEntries) {
        this.quantidadeEntries = quantidadeEntries;
        this.entries = new ArrayList<>();
    }

    @Override
    public T acessEntry(Long accessID) throws MissInterruption {
        for (Pair<Integer, T> pairAtual : entries) {
            T entryAtual = pairAtual.getPair2();
            if (entryAtual.equals(accessID)) {
                this.adicionaAcesso(pairAtual);
                return entryAtual;
            }
        }
        throw new MissInterruption();
    }
    
    private void adicionaAcesso(Pair<Integer, T> pairAtual) {
        Integer adiciona = pairAtual.getPair1() + 1;
        pairAtual.setPair1(adiciona);
    }

    @Override
    public T addEntry(T entry) {
        T result = null;
        Pair<Integer, T> entryPair = new Pair<Integer,T>(1, entry);
        if (quantidadeEntries == entries.size()) {
            result = this.removeMenosAcesso().getPair2();
        }
        entries.add(entryPair); 
        return result; 
    }

    private Pair<Integer, T> removeMenosAcesso() {
        Pair<Integer, T> menorAcesso = entries.getFirst();
        for (Pair<Integer, T> i : entries) {
            if (i.getPair1() < menorAcesso.getPair1()) {
                menorAcesso = i;
            }
        }
        entries.remove(menorAcesso);
        return menorAcesso;
    }

    @Override
    public String nomeToString() {
        return "LFU";
    }

    @Override
    public Long getQtdEntries() {
        return this.quantidadeEntries;
    }

}
