package page.x.memoriafisica;

import java.util.HashMap;

import page.x.TLB.algoritmos.substituicao.AlgoritmoSubstituicaoI;
import page.x.interruptions.Interruption;
import page.x.pagetable.PageTable;
import page.x.utils.Sorteador;

public class MemoriaFisica {

    private PageTable pageTable;
    private Long bitsParaRepresentarPageFrame;
    private HashMap<Long, PageFrameContent> memoriaFisica;
    private AlgoritmoSubstituicaoI<Long> algoritmoSubstituicao;
    private Sorteador sorteador;
    private Long tamanhoDaPaginaEmBytes;

    public MemoriaFisica (Long qtdBits, Long tamanhoDaPaginaEmBytes, AlgoritmoSubstituicaoI<Long> algoritmoSubstituicao) {
        this.tamanhoDaPaginaEmBytes = tamanhoDaPaginaEmBytes;
        this.algoritmoSubstituicao = algoritmoSubstituicao;
        this.bitsParaRepresentarPageFrame = this.bitsParaRepresentarPageFrame(qtdBits, tamanhoDaPaginaEmBytes);
        this.pageTable = new PageTable(qtdBits, tamanhoDaPaginaEmBytes);
        this.memoriaFisica = new HashMap<>();
        this.sorteador = new Sorteador(bitsParaRepresentarPageFrame);
    }

    public Long alocarPageFrame () {
        Long pageFrameAleatorio = sorteador.sortearNumero(memoriaFisica);
        memoriaFisica.put(pageFrameAleatorio, new PageFrameContent(this.tamanhoDaPaginaEmBytes));
        Long removedPage = algoritmoSubstituicao.addEntry(pageFrameAleatorio);
        if (removedPage != null) {
            memoriaFisica.remove(removedPage);
        }
        return pageFrameAleatorio;
    }

    public Integer getUtilizacaoMemoriaFisica() {
        return memoriaFisica.size();
    }

    private Long bitsParaRepresentarPageFrame(Long qtdBits, Long tamanhoDaPaginaEmBytes) {
        int tamanhoPaginaEmBits = (int)(Math.log(tamanhoDaPaginaEmBytes) / Math.log(2));
        return qtdBits - tamanhoPaginaEmBits;
    }

    public void acessarEndereco(Long PFN, Long offset) {
        this.memoriaFisica.get(PFN).acessarEndereco(offset);
        try {
            this.algoritmoSubstituicao.acessEntry(PFN);
        } catch (Interruption e) {
            e.printStackTrace();
        }
    }

    public PageTable getPageTable() {
        return pageTable;
    }
}