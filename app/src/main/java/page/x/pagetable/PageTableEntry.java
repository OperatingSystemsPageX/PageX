package page.x.pagetable;

public class PageTableEntry {
    private Long pageFrameId;
    private boolean mappedBit;
    private VirtualPage virtualPage;

    public PageTableEntry(Long pageFrameId, boolean mappedBit, VirtualPage virtualPage) {
        this.pageFrameId = pageFrameId;
        this.mappedBit = mappedBit;
        this.virtualPage = virtualPage;
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

    public Long getVirtualPageId() {
        return this.virtualPage.getId();
    }

}
