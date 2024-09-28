package page.x.TLB.algoritmos.substituicao;

import page.x.TLB.TlbEntry;
import page.x.interruptions.MissInterruption;

import java.util.LinkedList;
import java.util.Queue;

public class FIFO implements AlgoritmoSubstituicaoI {

    private int quantidadeEntries;

    private Queue<TlbEntry> entries;

    public FIFO(int quantidadeEntries) {
        this.quantidadeEntries = quantidadeEntries;
        this.entries = new LinkedList<>();
    }

    @Override
    public Long mapearPagina(Long vpn) throws MissInterruption {
        for (TlbEntry entryAtual : entries) {
            if (entryAtual.getVirtualPageNumber().equals(vpn)) {
                return entryAtual.getPageFrameNumber();
            }
        }
        throw new MissInterruption();
    }

    @Override
    public void addPaginaMapeada(Long vpn, Long pfn) {
        TlbEntry tlbEntry = new TlbEntry(vpn, pfn);
        if (quantidadeEntries == this.entries.size()) {
            this.entries.remove();
        }
        this.entries.add(tlbEntry);
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
