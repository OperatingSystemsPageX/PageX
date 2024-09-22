package page.x;

import java.util.HashMap;

import page.x.interruptions.FullPhysicalMemoryInterruption;
import page.x.pagetable.PageTable;
import page.x.utils.Sorteador;

public class MemoriaFisica {
    private PageTable pageTable;
    private Long bitsParaRepresentarPageFrame;
    private HashMap<Long, Long> memoriaFisica;
    private Sorteador sorteador;

    public MemoriaFisica (Long qtdBits, Long tamanhoPaginaEmBits) {
        this.bitsParaRepresentarPageFrame = bitsParaRepresentarPageFrame(qtdBits, tamanhoPaginaEmBits);
        this.pageTable = new PageTable(qtdBits, tamanhoPaginaEmBits);
        this.memoriaFisica = new HashMap<>();
        this.sorteador = new Sorteador(bitsParaRepresentarPageFrame);
    }

    public Long alocarPageFrame () throws FullPhysicalMemoryInterruption {
        if (getUtilizacaoMemoriaFisica() >= Math.pow(2, bitsParaRepresentarPageFrame)) {
            throw new FullPhysicalMemoryInterruption();
        }

        Long pageFrameAleatorio = sorteador.sortearNumero(memoriaFisica);

        memoriaFisica.put(pageFrameAleatorio, 0L);
        return pageFrameAleatorio;
    }

    public Integer getUtilizacaoMemoriaFisica() {
        return memoriaFisica.size();
    }

    private Long bitsParaRepresentarPageFrame(Long qtdBits, Long tamanhoPaginaEmBits) {
        return qtdBits - tamanhoPaginaEmBits;
    }
}