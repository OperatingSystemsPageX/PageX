package page.x.TLB.algoritmos.substituicao;

import java.util.Queue;
import java.util.LinkedList;

import page.x.TLB.TlbEntry;
import page.x.interruptions.MissInterruption;
import page.x.utils.Pair;

public class SecondChance implements AlgoritmoSubstituicaoI {

    private int quantidadeEntries;

    private Queue<Pair<Boolean, TlbEntry>> entries;

    public SecondChance(int quantidadeEntries) {
        this.quantidadeEntries = quantidadeEntries;
        this.entries = new LinkedList<>();
    }

    @Override
    public Integer mapearPagina(Integer vpn) throws MissInterruption {
        for (Pair<Boolean, TlbEntry> pairAtual : entries) {
            TlbEntry entryAtual = pairAtual.getPair2();
            if (entryAtual.getVirtualPageNumber().equals(vpn)) {
                pairAtual.setPair1(true);
                return entryAtual.getPageFrameNumber();
            }
        }
        throw new MissInterruption();
    }

    @Override
    public void addPaginaMapeada(Integer vpn, Integer pfn) {
        TlbEntry tlbEntry = new TlbEntry(vpn, pfn);
        Pair<Boolean, TlbEntry> tlbEntryPair = new Pair<Boolean,TlbEntry>(false, tlbEntry);
        if (entries.size() == quantidadeEntries) {
            this.removePagina();
        }
        this.entries.add(tlbEntryPair);
        
    }

    private void removePagina() {
        Pair<Boolean, TlbEntry> tlbEntryPair = this.entries.poll();
        while(tlbEntryPair.getPair1()) {
            tlbEntryPair.setPair1(false);
            this.entries.add(tlbEntryPair);
            tlbEntryPair = this.entries.remove();
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
}
