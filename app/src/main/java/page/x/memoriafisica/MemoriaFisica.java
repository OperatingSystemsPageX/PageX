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
    private Long tamanhoPaginaEmKB;

    public MemoriaFisica (Long qtdBits, Long tamanhoPaginaEmKB, AlgoritmoSubstituicaoI<Long> algoritmoSubstituicao) {
        this.tamanhoPaginaEmKB = tamanhoPaginaEmKB;
        this.algoritmoSubstituicao = algoritmoSubstituicao;
        this.bitsParaRepresentarPageFrame = this.bitsParaRepresentarPageFrame(qtdBits, tamanhoPaginaEmKB);
        this.pageTable = new PageTable(qtdBits, tamanhoPaginaEmKB);
        this.memoriaFisica = new HashMap<>();
        this.sorteador = new Sorteador(bitsParaRepresentarPageFrame);
    }

    public Long alocarPageFrame () {
        Long pageFrameAleatorio = sorteador.sortearNumero(memoriaFisica);
        memoriaFisica.put(pageFrameAleatorio, new PageFrameContent(this.tamanhoPaginaEmKB));
        Long removedPage = algoritmoSubstituicao.addEntry(pageFrameAleatorio);
        if (removedPage != null) {
            memoriaFisica.remove(removedPage);
        }
        return pageFrameAleatorio;
    }

    public Integer getUtilizacaoMemoriaFisica() {
        return memoriaFisica.size();
    }

    private Long bitsParaRepresentarPageFrame(Long qtdBits, Long tamanhoPaginaEmKB) {
        int tamanhoPaginaEmBits = 10 + (int)(Math.log(tamanhoPaginaEmKB) / Math.log(2));
        return qtdBits - tamanhoPaginaEmBits;
    }

    public void acessarEndereco(Long PFN, Long offset) {
        this.memoriaFisica.get(PFN).acessarEndereco(offset);
        try {
            this.algoritmoSubstituicao.acessEntry(PFN);
        } catch (Interruption e) {
            e.printStackTrace();
        }
        System.out.printf("%.5f", this.memoriaFisica.get(PFN).getPercentualDeUso());
    }

    public PageTable getPageTable() {
        return pageTable;
    }
}