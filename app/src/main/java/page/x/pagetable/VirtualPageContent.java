package page.x.pagetable;
import java.util.ArrayList;

public class VirtualPageContent {
    private ArrayList<String> enderecos;

    public VirtualPageContent () {
        this.enderecos = new ArrayList<>();
    }

    public ArrayList<String> getEnderecos() {
        return enderecos;
    }
}
