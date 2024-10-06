package page.x;

import org.junit.jupiter.api.Test;

import page.x.TLB.TLB;
import page.x.TLB.TlbEntry;
import page.x.TLB.algoritmos.substituicao.FIFO;
import page.x.interruptions.MissInterruption;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

class TestFifo {

    private final static int SIZE_FIFO = 5;
    private final static int QTD_PAIR = 10;
    private FIFO fifo1;
    private TLB tlb1;
    private TlbEntry[] entries;
    
    void preencheTLB(TLB tlb, TlbEntry[] entries, Integer size) throws MissInterruption{
        for (int i = 0; i < size; i++) {
            tlb.addPaginaMapeada(entries[i].getVirtualPageNumber(), entries[i].getPageFrameNumber());
        }
    } 

    @BeforeEach
    void setup() {
        this.fifo1 = new FIFO(SIZE_FIFO);
        this.tlb1 = new TLB(fifo1);
        this.entries = new TlbEntry[QTD_PAIR];
        for (int i = 0; i < QTD_PAIR; i++) {
            this.entries[i] = new TlbEntry((long) i, (long) i*2);
        }
    }

    @DisplayName("Testando logica da FIFO")
    @Test
    void testandoLogicaDaFifo() throws MissInterruption {
        this.preencheTLB(tlb1, entries, SIZE_FIFO);
        this.tlb1.mapearPagina(0L);
        this.tlb1.addPaginaMapeada(1000L, 2000L);
        assertThrows(MissInterruption.class, () -> this.tlb1.mapearPagina(0L));
        this.tlb1.mapearPagina(1L);
        this.tlb1.mapearPagina(1000L);
        this.tlb1.addPaginaMapeada(2000L, 3000L);
        assertAll(
            () -> assertThrows(MissInterruption.class, () -> this.tlb1.mapearPagina(1L)),
            () -> assertEquals(3, tlb1.getHit()),
            () -> assertEquals(2, tlb1.getMiss())
        );
    }
    
}

