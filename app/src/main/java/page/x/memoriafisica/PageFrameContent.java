package page.x.memoriafisica;
import java.util.Map;
import java.util.HashMap;

public class PageFrameContent {
    private Map<Long, String> enderecos;

    private Long tamanhoDaPaginaEmBytes;

    public PageFrameContent (Long tamanhoDaPaginaEmBytes) {
        this.tamanhoDaPaginaEmBytes = tamanhoDaPaginaEmBytes;
        this.enderecos = new HashMap<>();
    }

    public void acessarEndereco(Long offset) {
        this.enderecos.put(offset, "CONTENT");
    }

    public double getPercentualDeUso() {
        double percentual = ( (double) enderecos.size() / tamanhoDaPaginaEmBytes);
        return percentual * 100;
    }

    public Map<Long, String> getEnderecos() {
        return this.enderecos;
    }
}
