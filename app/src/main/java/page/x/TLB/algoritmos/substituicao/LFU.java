package page.x.TLB.algoritmos.substituicao;

import page.x.interruptions.MissInterruption;

import page.x.TLB.TlbEntry;
import page.x.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class LFU implements AlgoritmoSubstituicaoI {
    
    private int quantidadeEntries;

    private List<Pair<Long, TlbEntry>> entries;

    public LFU(int quantidadeEntries) {
        this.quantidadeEntries = quantidadeEntries;
        this.entries = new ArrayList<>();
    }

    @Override
    public Long mapearPagina(Long vpn) throws MissInterruption {
        for (Pair<Long, TlbEntry> pairAtual : entries) {
            TlbEntry entryAtual = pairAtual.getPair2();
            if (entryAtual.getVirtualPageNumber().equals(vpn)) {
                this.adicionaAcesso(pairAtual);
                return entryAtual.getPageFrameNumber();
            }
        }
        throw new MissInterruption();
    }
    
    private void adicionaAcesso(Pair<Long, TlbEntry> pairAtual) {
        Long adiciona = pairAtual.getPair1() + 1;
        pairAtual.setPair1(adiciona);
    }

    @Override
    public void addPaginaMapeada(Long vpn, Long pfn) {
        TlbEntry tlbEntry = new TlbEntry(vpn, pfn);
        Pair<Long, TlbEntry> tlbEntryPair = new Pair<Long,TlbEntry>(1L, tlbEntry);
        if (quantidadeEntries == entries.size()) {
            this.removeMenosAcesso();
        }
        entries.add(tlbEntryPair);  
    }

    private void removeMenosAcesso() {
        Pair<Long, TlbEntry> menorAcesso = entries.getFirst();
        for (Pair<Long, TlbEntry> i : entries) {
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
}
