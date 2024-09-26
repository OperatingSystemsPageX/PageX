package page.x.estados;

public class EnderecoVirtual {
    private int PFN;
    private int offset;

    public EnderecoVirtual(int PFN, int offset) {
        this.PFN = PFN;
        this.offset = offset;
    }

    public int getPFN() {
        return PFN;
    }

    public int getOffset() {
        return offset;
    }
}
