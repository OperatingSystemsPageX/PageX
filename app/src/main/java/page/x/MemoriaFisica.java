package page.x;

import java.util.HashMap;

import page.x.pagetable.PageTable;
import page.x.utils.MathUtils;

public class MemoriaFisica {
    private PageTable pageTable;
    private Integer quantidadeDePageFrames;
    private HashMap<Integer, Integer> memoriaFisica;

    public MemoriaFisica (Integer quantidadeDePageFrames) {
        this.quantidadeDePageFrames = quantidadeDePageFrames;
        this.pageTable = new PageTable();
        this.memoriaFisica = new HashMap<>();
    }

    public void alocarPageFrame () {
        Integer pageFrameAleatorio = MathUtils.PegarNumeroAleatorio(pageTable.getTamanhoEmPaginas() + 1, quantidadeDePageFrames);
        memoriaFisica.put(pageFrameAleatorio, 0);
    }
}