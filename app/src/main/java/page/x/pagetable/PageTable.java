package page.x.pagetable;
import page.x.interruptions.PageFaultInterruption;
import java.util.HashMap;

public class PageTable {
    private HashMap<Long, PageTableEntry> pageTable; // a key, no caso, Long é o vpn = id da page = indice da pte
    private Long tamanhoPte; // considerar o tamanhoPte em bytes

    public PageTable(Long qtdBits, Long tamanhoPaginaEmBits) {
        this.pageTable = new HashMap<>();
    }

    public void addPage(VirtualPage virtualPage, Long pageFrameId) {
        Long vpn = virtualPage.getId();
        pageTable.put(vpn, new PageTableEntry(pageFrameId, true, virtualPage)); // vpn = virtual page number
    }

    private boolean estaPresenteNaPT(Long vpn) throws PageFaultInterruption {
        PageTableEntry pte = pageTable.get(vpn);
        if (pte == null || !pte.estaMapeada()) {
            throw new PageFaultInterruption();
        }
        return true;
    }

    public PageTableEntry acessarMemoria(Long vpn) throws PageFaultInterruption {
        if (estaPresenteNaPT(vpn)) {
            return pageTable.get(vpn);
        } else {
            return handlePageFault(vpn);
        }
    }

    // As funções abaixo serão movidas para o InterruptionHandler
    private Long getPageFrameLivre() {
        // a ideia é pegar um page frame livre na memoria fisica e retornar para o handler
        return 0L;
    }

    private PageTableEntry handlePageFault(Long vpn) {
        VirtualPage page = new VirtualPage(vpn);
        addPage(page, getPageFrameLivre());
        return pageTable.get(vpn);
    }
    //

    public Long getTamanhoPte() {
        return tamanhoPte;
    }

    public Long getQtdDePaginas() {
        return 10L;
    }

    public Long getTamanho() {
        return pageTable.size() * this.tamanhoPte;
    }

    @SuppressWarnings("unused")
    private void tamanhoPte(Long bits) {
        // implements
    }
}
