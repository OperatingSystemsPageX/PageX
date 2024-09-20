package page.x.pagetable;

public class PageTableEntry {
    private int pageFrameId;
    private boolean mappedBit;
    public PageTableEntry(int pageFrameId, boolean mappedBit) {
        this.pageFrameId = pageFrameId;
        this.mappedBit = mappedBit;
    }

    public int getPageFrameId() {
        return pageFrameId;
    }

    public void setPageFrameId(int pageFrameId) {
        this.pageFrameId = pageFrameId;
    }

    public boolean isMapped() {
        return mappedBit;
    }

    public void setMapped(boolean mappedBit) {
        this.mappedBit = mappedBit;
    }

}
