package page.x.pagetable;
import page.x.interruptions.PageFaultInterruption;
import java.util.HashMap;

public class PageTable {
    private HashMap<Long, PageTableEntry> pageTable; // a key, no caso, Long é o vpn = id da page = indice da pte
    private Long tamanhoPte; // considerar o tamanhoPte em bytes

    public PageTable(Long qtdBits, Long tamanhoPaginaEmBits) {
        this.pageTable = new HashMap<>();
    }

    public void addPage(Long virtualPageNumber, Long pageFrameId) {
        VirtualPageContent virtualPageContent = new VirtualPageContent();
        pageTable.put(virtualPageNumber, new PageTableEntry(pageFrameId, true, virtualPageContent)); // vpn = virtual page number
    }

    private boolean estaPresenteNaPT(Long vpn) throws PageFaultInterruption {
        PageTableEntry pte = pageTable.get(vpn);
        if (pte == null || !pte.estaMapeada()) {
            throw new PageFaultInterruption();
        }
        return true;
    }

    public Long getTamanhoPte() {
        return tamanhoPte;
    }

    public Long getQtdDePaginas() {
        return 10L;
    }

    public Long getTamanho() {
        return pageTable.size() * this.tamanhoPte;
    }

    @SuppressWarnings("unused")
    private void tamanhoPte(Long bits) {
        // implements
    }
}
