package page.x.pagetable;

public class PageTableEntry {
    private Integer pageFrameId;
    private boolean mappedBit;
    private VirtualPage virtualPage;

    public PageTableEntry(Integer pageFrameId, boolean mappedBit, VirtualPage virtualPage) {
        this.pageFrameId = pageFrameId;
        this.mappedBit = mappedBit;
        this.virtualPage = virtualPage;
    }

    public Integer getPageFrameId() {
        return pageFrameId;
    }

    public void setPageFrameId(Integer pageFrameId) {
        this.pageFrameId = pageFrameId;
    }

    public boolean estaMapeada() {
        return mappedBit;
    }

    public void mapear(boolean mappedBit) {
        this.mappedBit = mappedBit;
    }

    public Integer getVirtualPageId() {
        return this.virtualPage.getId();
    }

}
