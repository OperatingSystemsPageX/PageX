package page.x.memoriafisica;
import java.util.Map;
import java.util.HashMap;

public class PageFrameContent {
    private Map<Long, String> enderecos;

    private Long tamanhoPaginaEmKB;

    public PageFrameContent (Long tamanhoPaginaEmKB) {
        this.tamanhoPaginaEmKB = tamanhoPaginaEmKB;
        this.enderecos = new HashMap<>();
    }

    public void acessarEndereco(Long offset) {
        this.enderecos.put(offset, "CONTENT");
    }

    public double getPercentualDeUso() {
        double tamanhoPaginaBytes = (double) tamanhoPaginaEmKB * 1024;
        double percentual = ( (double) enderecos.size() / tamanhoPaginaBytes);
        return percentual * 100;
    }

    public Map<Long, String> getEnderecos() {
        return this.enderecos;
    }
}
