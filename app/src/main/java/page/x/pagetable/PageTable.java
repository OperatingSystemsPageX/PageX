package page.x.pagetable;
import page.x.interruptions.PageFaultInterruption;
import java.util.HashMap;

public class PageTable {
    private HashMap<Integer, PageTableEntry> pageTable; // a key, no caso, integer é o vpn = id da page = indice da pte
    private Integer tamanhoPte; // considerar o tamanhoPte em bytes

    public PageTable() {
        this.pageTable = new HashMap<>();
    }

    public void addPage(VirtualPage virtualPage, Integer pageFrameId) {
        Integer vpn = virtualPage.getId();
        pageTable.put(vpn, new PageTableEntry(pageFrameId, true, virtualPage)); // vpn = virtual page number
    }

    private boolean estaPresenteNaPT(Integer vpn) throws PageFaultInterruption {
        PageTableEntry pte = pageTable.get(vpn);
        if (pte == null || !pte.estaMapeada()) {
            throw new PageFaultInterruption();
        }
        return true;
    }

    public PageTableEntry acessarMemoria(Integer vpn) throws PageFaultInterruption {
        if (estaPresenteNaPT(vpn)) {
            return pageTable.get(vpn);
        } else {
            return handlePageFault(vpn);
        }
    }

    // As funções abaixo serão movidas para o InterruptionHandler
    private Integer getPageFrameLivre() {
        // a ideia é pegar um page frame livre na memoria fisica e retornar para o handler
        return 0;
    }

    private PageTableEntry handlePageFault(Integer vpn) {
        VirtualPage page = new VirtualPage(vpn);
        addPage(page, getPageFrameLivre());
        return pageTable.get(vpn);
    }
    //

    public Integer getTamanhoPte() {
        return tamanhoPte;
    }

    public Integer getQtdDePaginas() {
        return pageTable.size();
    }

    public Integer getTamanho() {
        return pageTable.size() * this.tamanhoPte;
    }

    @SuppressWarnings("unused")
    private void tamanhoPte(Integer bits) {
        // implements
    }
}
