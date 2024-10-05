package page.x.TLB.algoritmos.substituicao;

import page.x.interruptions.MissInterruption;

import page.x.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class LFU<T> implements AlgoritmoSubstituicaoI<T> {
    
    private int quantidadeEntries;

    private List<Pair<Integer, T>> entries;

    public LFU(int quantidadeEntries) {
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
    public void addEntry(T entry) {
        Pair<Integer, T> entryPair = new Pair<Integer,T>(1, entry);
        if (quantidadeEntries == entries.size()) {
            this.removeMenosAcesso();
        }
        entries.add(entryPair);  
    }

    private void removeMenosAcesso() {
        Pair<Integer, T> menorAcesso = entries.getFirst();
        for (Pair<Integer, T> i : entries) {
            if (i.getPair1() < menorAcesso.getPair1()) {
                menorAcesso = i;
            }
        }
        entries.remove(menorAcesso);
    }

    @Override
    public String nomeToString() {
        return "LFU";
    }

    @Override
    public int getQtdEntries() {
        return this.quantidadeEntries;
    }

    @Override
    public void reset() {
        this.entries = new ArrayList<>();
    }
}
