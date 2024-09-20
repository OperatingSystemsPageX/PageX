package page.x.pagetable;

import page.x.interruptions.PageFaultInterruption;

import java.util.HashMap;

public class PageTable {
    private HashMap<Integer, PageTableEntry> pageTable;
    private int sizePte; // considering sizePte in bytes

    public PageTable() {
        this.pageTable = new HashMap<>();
    }

    public void addPage(int vpn, int pageFrameId) {
        pageTable.put(vpn, new PageTableEntry(pageFrameId, true)); // vpn means virtual page number
    }

    private boolean isPresent(int vpn) throws PageFaultInterruption {
        PageTableEntry pte = pageTable.get(vpn);
        if (pte == null || !pte.isMapped()) {
            throw new PageFaultInterruption();
        }
        return true;
    }

    public PageTableEntry acessMemory(int vpn) throws PageFaultInterruption {
        if (isPresent(vpn)) {
            return pageTable.get(vpn);
        } else {
            return handlePageFault(vpn);
        }
    }

    private int getFreePageFrame() {
        // the idea is to get a free page frame in physical memory and return its id to the handler
        return 0;
    }

    private PageTableEntry handlePageFault(int vpn) {
        addPage(vpn, getFreePageFrame());
        return pageTable.get(vpn);
    }

    public int getSizePte() {
        return sizePte;
    }

    private void sizePte(int bits) {
        // implements
    }

    public int size() {
        return pageTable.size() * this.sizePte;
    }

}
