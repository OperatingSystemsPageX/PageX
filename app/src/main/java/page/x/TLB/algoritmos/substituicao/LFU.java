package page.x.TLB.algoritmos.substituicao;

import page.x.interruptions.MissInterruption;

import page.x.TLB.TlbEntry;
import page.x.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class LFU implements AlgoritmoSubstituicaoI {
    
    private int quantidadeEntries;

    private List<Pair<Integer, TlbEntry>> entries;

    public LFU(int quantidadeEntries) {
        this.quantidadeEntries = quantidadeEntries;
        this.entries = new ArrayList<>();
    }

    @Override
    public Long mapearPagina(Long vpn) throws MissInterruption {
        for (Pair<Integer, TlbEntry> pairAtual : entries) {
            TlbEntry entryAtual = pairAtual.getPair2();
            if (entryAtual.getVirtualPageNumber().equals(vpn)) {
                this.adicionaAcesso(pairAtual);
                return entryAtual.getPageFrameNumber();
            }
        }
        throw new MissInterruption();
    }
    
    private void adicionaAcesso(Pair<Integer, TlbEntry> pairAtual) {
        Integer adiciona = pairAtual.getPair1() + 1;
        pairAtual.setPair1(adiciona);
    }

    @Override
    public void addPaginaMapeada(Long vpn, Long pfn) {
        TlbEntry tlbEntry = new TlbEntry(vpn, pfn);
        Pair<Integer, TlbEntry> tlbEntryPair = new Pair<Integer,TlbEntry>(1, tlbEntry);
        if (quantidadeEntries == entries.size()) {
            this.removeMenosAcesso();
        }
        entries.add(tlbEntryPair);  
    }

    private void removeMenosAcesso() {
        Pair<Integer, TlbEntry> menorAcesso = entries.getFirst();
        for (Pair<Integer, TlbEntry> i : entries) {
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
