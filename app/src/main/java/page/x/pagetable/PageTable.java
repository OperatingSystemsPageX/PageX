package page.x.pagetable;
import java.util.HashMap;

public class PageTable {
    private HashMap<Long, PageTableEntry> pageTable; // a key, no caso, Long é o vpn = id da page = indice da pte
    private Long tamanhoPte; // considerar o tamanhoPte em bytes
    private Long tamanhoPaginaEmKB;
    private Long tamanhoEnderecoEmBits;
    public PageTable(Long qtdBits, Long tamanhoPaginaEmKB) {
        this.pageTable = new HashMap<>();
        this.tamanhoPaginaEmKB = tamanhoPaginaEmKB;
        this.tamanhoEnderecoEmBits = qtdBits;
        tamanhoPteBytes();
    }

    public void addPage(Long virtualPageNumber, Long pageFrameId) {
        pageTable.put(virtualPageNumber, new PageTableEntry(pageFrameId, true)); // vpn = virtual page number
    }

    public PageTableEntry mapearPagina(Long vpn) {
        PageTableEntry pageTableEntryExistente = pageTable.get(vpn);
        if (pageTableEntryExistente == null) {
            // Simula o estado de uma PTE quando o VPN nunca foi utilizado antes
            PageTableEntry pageTableEntryNova = new PageTableEntry(0L, false);
            pageTable.put(vpn, pageTableEntryNova);
            return pageTableEntryNova;
        }
        return pageTableEntryExistente;
    }

    public Long getTamanhoPte() {
        return tamanhoPte;
    }

    public Long getQtdDePaginas() {
        long qtdPaginas = (long) Math.pow(2, tamanhoEnderecoEmBits) / (tamanhoPaginaEmKB * 1024);
        return qtdPaginas;
    }

    public Long getTamanho() {
        return getQtdDePaginas() * this.tamanhoPte;
    }

    private void tamanhoPteBytes() {
        long bitsParaRepresentarPage = (int) (Math.log(tamanhoPaginaEmKB) / Math.log(2));
        this.tamanhoPte = ((bitsParaRepresentarPage + 7) / 8) + 1;
    }
}
