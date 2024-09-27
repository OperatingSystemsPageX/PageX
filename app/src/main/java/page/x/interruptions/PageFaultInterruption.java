package page.x.interruptions;

import page.x.estados.EnderecoVirtual;
import page.x.pagetable.PageTableEntry;

public class PageFaultInterruption extends Exception {
    private EnderecoVirtual enderecoVirtual;
    private PageTableEntry pageTableEntry;

    public PageFaultInterruption() {
        super("PAGE FAULT");
    }

    public PageFaultInterruption(PageTableEntry pageTableEntry,EnderecoVirtual enderecoVirtual) {
        this.enderecoVirtual = enderecoVirtual;
        this.pageTableEntry = pageTableEntry;
    }

    public EnderecoVirtual getEnderecoVirtual() {
        return enderecoVirtual;
    }

    public PageTableEntry getPageTableEntry() {
        return pageTableEntry;
    }
}