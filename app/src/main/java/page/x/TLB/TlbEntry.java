package page.x.TLB;

public class TlbEntry {

    private Long virtualPageNumber;

    private Long pageFrameNumber;

    public TlbEntry(Long virtualPageNumber, Long pageFrameNumber) {
        this.virtualPageNumber = virtualPageNumber;
        this.pageFrameNumber = pageFrameNumber;
    }

    public Long getVirtualPageNumber() {
        return virtualPageNumber;
    }

    public Long getPageFrameNumber() {
        return pageFrameNumber;
    }
    
}
