package page.x.entry;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TlbEntry) {
            TlbEntry other = (TlbEntry) obj;
            return virtualPageNumber.equals(other.virtualPageNumber)
                    && pageFrameNumber.equals(other.pageFrameNumber);
        } else if (obj instanceof Long) {
            Long other = (Long) obj;
            return virtualPageNumber.equals(other);
        }
        return false;
    }

}