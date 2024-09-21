package page.x.pagetable;

public class PageTableEntry {
    private Integer pageFrameId;
    private boolean mappedBit;
    public PageTableEntry(Integer pageFrameId, boolean mappedBit) {
        this.pageFrameId = pageFrameId;
        this.mappedBit = mappedBit;
    }

    public Integer getPageFrameId() {
        return pageFrameId;
    }

    public void setPageFrameId(Integer pageFrameId) {
        this.pageFrameId = pageFrameId;
    }

    public boolean isMapped() {
        return mappedBit;
    }

    public void setMapped(boolean mappedBit) {
        this.mappedBit = mappedBit;
    }

}
