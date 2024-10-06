package page.x;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.x.pagetable.PageTable;
import page.x.pagetable.PageTableEntry;

import static org.junit.jupiter.api.Assertions.*;

class TestPageTable {

    private PageTable pageTable;

    @BeforeEach
    public void setUp() {
        pageTable = new PageTable(16L, 2048L);
    }

    @Test
    public void testAddPage() {
        Long vpn = 1L;
        Long pageFrameId = 3L;

        pageTable.addPage(vpn, pageFrameId);

        PageTableEntry entry = pageTable.mapearPagina(vpn);
        assertNotNull(entry, "PageTableEntry não deve ser nula após adicionar uma página");
        assertEquals(pageFrameId, entry.getPageFrameNumber(), "Page Frame ID incorreto após adicionar");
        assertTrue(entry.estaMapeada(), "A página deveria estar mapeada após ser adicionada");
    }

    @Test
    public void testMapearPaginaNaoMapeada() {
        Long vpn = 5L;
        PageTableEntry entry = pageTable.mapearPagina(vpn);
        assertNotNull(entry, "PageTableEntry não deve ser nula");
        assertEquals(0L, entry.getPageFrameNumber(), "Page Frame ID deveria ser 0 para páginas não mapeadas");
        assertFalse(entry.estaMapeada(), "MappedBit deveria ser falso para páginas não mapeadas");
    }

    @Test
    public void testTamanhoDaPageTable() {
        Long tamanhoEsperado = 96L;
        assertEquals(tamanhoEsperado, pageTable.getTamanho(), "Tamanho da Page Table está incorreto");
    }

    @Test
    public void testGetQtdDePaginas() {
        Long qtdEsperada = 32L;
        assertEquals(qtdEsperada, pageTable.getQtdDePaginas(), "Quantidade de páginas está incorreta");
    }
}

class PageTableEntryTest {

    @Test
    public void testPageFrameNumber() {
        Long pageFrameNumber = 10L;
        PageTableEntry entry = new PageTableEntry(pageFrameNumber, true);

        assertEquals(pageFrameNumber, entry.getPageFrameNumber(), "Page Frame Number está incorreto");

        entry.setPageFrameNumber(20L);
        assertEquals(20L, entry.getPageFrameNumber(), "O valor de Page Frame Number não foi atualizado corretamente");
    }

    @Test
    public void testMappedBit() {
        PageTableEntry entry = new PageTableEntry(5L, false);

        assertFalse(entry.estaMapeada(), "MappedBit deveria ser falso inicialmente");

        entry.mapear(true);
        assertTrue(entry.estaMapeada(), "MappedBit não foi atualizado corretamente para verdadeiro");
    }
}
