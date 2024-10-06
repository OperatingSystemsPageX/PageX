package page.x.pagetable;

public class PageTableEntry {
    private Long pageFrameNumber;
    private boolean mappedBit;

    public PageTableEntry(Long pageFrameNumber, boolean mappedBit) {
        this.pageFrameNumber = pageFrameNumber;
        this.mappedBit = mappedBit;
    }

    public Long getPageFrameNumber() {
        return pageFrameNumber;
    }

    public void setPageFrameNumber(Long pageFrameNumber) {
        this.pageFrameNumber = pageFrameNumber;
    }

    public boolean estaMapeada() {
        return mappedBit;
    }

    public void mapear(boolean mappedBit) {
        this.mappedBit = mappedBit;
    }

}
