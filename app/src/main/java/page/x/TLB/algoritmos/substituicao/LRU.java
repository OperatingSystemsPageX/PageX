package page.x.TLB.algoritmos.substituicao;

import java.util.LinkedList;

import page.x.interruptions.MissInterruption;

public class LRU<T> implements AlgoritmoSubstituicaoI<T> {
    
    private int quantidadeEntries;
    
    private LinkedList<T> entries;

    public LRU(int quantidadeEntries) {
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
    public void addEntry(T entry) {
        if (quantidadeEntries == this.entries.size()) {
            this.entries.removeFirst();
        } 
        this.entries.addLast(entry);
    }

    @Override
    public String nomeToString() {
        return "LRU";
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
