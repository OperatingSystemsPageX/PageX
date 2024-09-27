package page.x.TLB.algoritmos.substituicao;

import java.util.LinkedList;

import page.x.TLB.TlbEntry;
import page.x.interruptions.MissInterruption;

public class LRU implements AlgoritmoSubstituicaoI {
    
    private int quantidadeEntries;
    
    private LinkedList<TlbEntry> entries;


    public LRU(int quantidadeEntries) {
        this.quantidadeEntries = quantidadeEntries;
        this.entries = new LinkedList<>();
    }

    @Override
    public Integer mapearPagina(Integer vpn) throws MissInterruption {
        for (TlbEntry entryAtual : this.entries) {
            if (entryAtual.getVirtualPageNumber().equals(vpn)) {
                this.execucaoAlgoritmo(entryAtual);
                return entryAtual.getPageFrameNumber();
            }
        }
        throw new MissInterruption();
    }

    private void execucaoAlgoritmo(TlbEntry entry) {
        this.entries.remove(entry);
        this.entries.addLast(entry);
    }    

    @Override
    public void addPaginaMapeada(Integer vpn, Integer pfn) {
        TlbEntry tlbEntry = new TlbEntry(vpn, pfn);
        if (quantidadeEntries == this.entries.size()) {
            this.entries.removeFirst();
        } 
        this.entries.addLast(tlbEntry);
    }

    @Override
    public String nomeToString() {
        return "LRU";
    }

    @Override
    public int getQtdEntries() {
        return this.quantidadeEntries;
    }
}
