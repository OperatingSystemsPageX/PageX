package page.x.pagetable;

import page.x.interruptions.PageFaultInterruption;

import java.util.HashMap;

public class PageTable {
    private HashMap<Integer, PageTableEntry> pageTable;
    private Integer tamanhoPte; // considering tamanhoPte in bytes

    public PageTable() {
        this.pageTable = new HashMap<>();
    }

    public void addPage(Integer vpn, Integer pageFrameId) {
        pageTable.put(vpn, new PageTableEntry(pageFrameId, true)); // vpn means virtual page number
    }

    private boolean isPresent(Integer vpn) throws PageFaultInterruption {
        PageTableEntry pte = pageTable.get(vpn);
        if (pte == null || !pte.isMapped()) {
            throw new PageFaultInterruption();
        }
        return true;
    }

    public PageTableEntry acessMemory(Integer vpn) throws PageFaultInterruption {
        if (isPresent(vpn)) {
            return pageTable.get(vpn);
        } else {
            return handlePageFault(vpn);
        }
    }

    // As funções abaixo serão movidas para o InterruptionHandler
    private Integer getFreePageFrame() {
        // the idea is to get a free page frame in physical memory and return its id to the handler
        return 0;
    }

    private PageTableEntry handlePageFault(Integer vpn) {
        addPage(vpn, getFreePageFrame());
        return pageTable.get(vpn);
    }

    public Integer getTamanhoPte() {
        return tamanhoPte;
    }

    public Integer getTamanhoEmPaginas() {
        return tamanhoPte;
    }

    public Integer getTamanho() {
        return pageTable.size() * this.tamanhoPte;
    }

    @SuppressWarnings("unused")
    private void tamanhoPte(Integer bits) {
        // implements
    }
}
