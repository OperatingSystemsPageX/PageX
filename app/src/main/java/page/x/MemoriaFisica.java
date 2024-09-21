package page.x;

import java.util.HashMap;

import page.x.interruptions.FullPhysicalMemoryInterruption;
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

    public Integer alocarPageFrame () throws FullPhysicalMemoryInterruption {
        if (getUtilizacaoMemoriaFisica() >= quantidadeDePageFrames) {
            throw new FullPhysicalMemoryInterruption();
        }

        Integer pageFrameAleatorio = MathUtils.PegarNumeroAleatorio(pageTable.getTamanhoEmPaginas() + 1, quantidadeDePageFrames);
        while (!memoriaFisica.containsKey(pageFrameAleatorio)) {
            pageFrameAleatorio = MathUtils.PegarNumeroAleatorio(pageTable.getTamanhoEmPaginas() + 1, quantidadeDePageFrames);
        }

        memoriaFisica.put(pageFrameAleatorio, 0);
        return pageFrameAleatorio;
    }

    public Integer getUtilizacaoMemoriaFisica() {
        return memoriaFisica.size();
    }
}