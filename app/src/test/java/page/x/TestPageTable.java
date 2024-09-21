package page.x;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.x.interruptions.PageFaultInterruption;
import page.x.pagetable.*;

import static org.junit.jupiter.api.Assertions.*;

class PageTableTest {

    private PageTable pageTableEmpty;
    private PageTable pageTable;


    @BeforeEach
    void setUp() {
        //pageTableEmpty = new PageTable();
        //pageTable = new PageTable();
    }

    @Test
    @DisplayName("Verifica o addPage para uma PT vazia")
    void testAddPageInEmptyPT() throws PageFaultInterruption {
        // Verifica o addPage para uma PT vazia
    }

    @Test
    @DisplayName("Verifica o addPage para uma PT já preenchida")
    void testAddPage() throws PageFaultInterruption {
        // Verifica o addPage para uma PT já preenchida
    }

    @Test
    void testAccessMemoryWithPresentPage() throws PageFaultInterruption {
        // Testa acesso à memória com uma página já presente

    }

    @Test
    void testAccessMemoryWithPageFault() {
        // Testa acesso à memória quando ocorre um page fault
    }

    @Test
    void testHandlePageFault() {
        // Testa se o page fault é tratado corretamente
    }
}
