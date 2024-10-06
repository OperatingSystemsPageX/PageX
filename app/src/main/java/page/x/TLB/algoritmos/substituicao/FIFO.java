package page.x.TLB.algoritmos.substituicao;

import page.x.interruptions.MissInterruption;

import java.util.LinkedList;
import java.util.Queue;

public class FIFO<T> implements AlgoritmoSubstituicaoI<T> {

    private Long quantidadeEntries;

    private Queue<T> entries;

    public FIFO(Long quantidadeEntries) {
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
    public T addEntry(T entry) {
        T result = null;
        if (quantidadeEntries == this.entries.size()) {
            result = this.entries.remove();
        }
        this.entries.add(entry);
        return result;
    }

    @Override
    public String nomeToString() {
        return "FIFO";
    }

    @Override
    public Long getQtdEntries() {
        return this.quantidadeEntries;
    }

    @Override
    public void reset() {
        this.entries = new LinkedList<>();
    }
}
