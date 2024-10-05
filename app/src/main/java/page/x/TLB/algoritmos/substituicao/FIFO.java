package page.x.TLB.algoritmos.substituicao;

import page.x.interruptions.MissInterruption;

import java.util.LinkedList;
import java.util.Queue;

public class FIFO<T> implements AlgoritmoSubstituicaoI<T> {

    private int quantidadeEntries;

    private Queue<T> entries;

    public FIFO(int quantidadeEntries) {
        this.quantidadeEntries = quantidadeEntries;
        this.entries = new LinkedList<>();
    }

    @Override
    public T acessEntry(Long accessID) throws MissInterruption {
        for (T entryAtual : entries) {
            if (entryAtual.equals(accessID)) {
                return entryAtual;
            }
        }
        throw new MissInterruption();
    }

    @Override
    public void addEntry(T entry) {
        if (quantidadeEntries == this.entries.size()) {
            this.entries.remove();
        }
        this.entries.add(entry);
    }

    @Override
    public String nomeToString() {
        return "FIFO";
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
