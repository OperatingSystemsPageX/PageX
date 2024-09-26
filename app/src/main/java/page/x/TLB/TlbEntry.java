package page.x.TLB;

public class TlbEntry {

    private Integer virtualPageNumber;

    private Integer pageFrameNumber;

    public TlbEntry(Integer virtualPageNumber, Integer pageFrameNumber) {
        this.virtualPageNumber = virtualPageNumber;
        this.pageFrameNumber = pageFrameNumber;
    }

    public Integer getVirtualPageNumber() {
        return virtualPageNumber;
    }

    public Integer getPageFrameNumber() {
        return pageFrameNumber;
    }
    
}
