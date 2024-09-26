package page.x.memoriafisica;

import java.util.HashMap;

import page.x.interruptions.FullPhysicalMemoryInterruption;
import page.x.pagetable.PageTable;
import page.x.utils.Sorteador;

public class MemoriaFisica {
    @SuppressWarnings("unused")
    private PageTable pageTable;
    private Long bitsParaRepresentarPageFrame;
    private HashMap<Long, PageFrameContent> memoriaFisica;
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

        memoriaFisica.put(pageFrameAleatorio, new PageFrameContent());

        return pageFrameAleatorio;
    }

    public Integer getUtilizacaoMemoriaFisica() {
        return memoriaFisica.size();
    }

    private Long bitsParaRepresentarPageFrame(Long qtdBits, Long tamanhoPaginaEmBits) {
        return qtdBits - tamanhoPaginaEmBits;
    }
}