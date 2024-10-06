package page.x.TLB.algoritmos.substituicao;

import java.util.LinkedList;

import page.x.interruptions.MissInterruption;

public class LRU<T> implements AlgoritmoSubstituicaoI<T> {
    
    private Long quantidadeEntries;
    
    private LinkedList<T> entries;

    public LRU(Long quantidadeEntries) {
        this.quantidadeEntries = quantidadeEntries;
        this.entries = new LinkedList<>();
    }

    @Override
    public T acessEntry(Long accessID) throws MissInterruption {
        for (T entryAtual : this.entries) {
            if (entryAtual.equals(accessID)) {
                this.execucaoAlgoritmo(entryAtual);
                return entryAtual;
            }
        }
        throw new MissInterruption();
    }

    private void execucaoAlgoritmo(T entry) {
        this.entries.remove(entry);
        this.entries.addLast(entry);
    }    

    @Override
    public T addEntry(T entry) {
        T result = null;
        if (quantidadeEntries == this.entries.size()) {
            result = this.entries.removeFirst();
        } 
        this.entries.addLast(entry);
        return result;
    }

    @Override
    public String nomeToString() {
        return "LRU";
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
