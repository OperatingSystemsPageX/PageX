package page.x.TLB.algoritmos.substituicao;

import java.util.Queue;
import java.util.LinkedList;

import page.x.interruptions.MissInterruption;
import page.x.utils.Pair;

public class SecondChance<T> implements AlgoritmoSubstituicaoI<T> {

    private int quantidadeEntries;

    private Queue<Pair<Boolean, T>> entries;

    public SecondChance(int quantidadeEntries) {
        this.quantidadeEntries = quantidadeEntries;
        this.entries = new LinkedList<>();
    }

    @Override
    public T acessEntry(Long accessID) throws MissInterruption {
        for (Pair<Boolean, T> pairAtual : entries) {
            T entryAtual = pairAtual.getPair2();
            if (entryAtual.equals(accessID)) {
                pairAtual.setPair1(true);
                return entryAtual;
            }
        }
        throw new MissInterruption();
    }

    @Override
    public void addEntry(T entry) {
        Pair<Boolean, T> tlbEntryPair = new Pair<Boolean,T>(false, entry);
        if (entries.size() == quantidadeEntries) {
            this.removePagina();
        }
        this.entries.add(tlbEntryPair);
        
    }

    private void removePagina() {
        Pair<Boolean, T> entryPair = this.entries.poll();
        while(entryPair.getPair1()) {
            entryPair.setPair1(false);
            this.entries.add(entryPair);
            entryPair = this.entries.remove();
        }
    }

    @Override
    public String nomeToString() {
        return "SecondChance";
    }

    @Override
    public int getQtdEntries() {
        return this.quantidadeEntries;
    }

    @Override
    public void reset() {
        this.entries = new LinkedList<>();
    }
}
