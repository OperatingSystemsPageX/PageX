package page.x.pagetable;

public class PageTableEntry {
    private Long pageFrameId;
    private boolean mappedBit;
    private VirtualPageContent virtualPageContent;

    public PageTableEntry(Long pageFrameId, boolean mappedBit, VirtualPageContent virtualPageContent) {
        this.pageFrameId = pageFrameId;
        this.mappedBit = mappedBit;
        this.virtualPageContent = virtualPageContent;
    }

    public Long getPageFrameId() {
        return pageFrameId;
    }

    public void setPageFrameId(Long pageFrameId) {
        this.pageFrameId = pageFrameId;
    }

    public boolean estaMapeada() {
        return mappedBit;
    }

    public void mapear(boolean mappedBit) {
        this.mappedBit = mappedBit;
    }

}
