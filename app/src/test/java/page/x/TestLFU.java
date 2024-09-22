package page.x;

import org.junit.jupiter.api.Test;

import page.x.TLB.TLB;
import page.x.TLB.algoritmos.substituicao.LFU;
import page.x.interruptions.MissInterruption;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

class TestLFU {

    private final static int SIZE_LFU = 5;
    private final static int QTD_PAIR = 10;
    private LFU lfu1;
    private TLB tlb1;
    private TlbEntry[] entries;
    
    void preencheTLB(TLB tlb, TlbEntry[] entries, Integer size) throws MissInterruption{
        for (int i = 0; i < size; i++) {
            tlb.addPaginaMapeada(entries[i].getVirtualPageNumber(), entries[i].getPageFrameNumber());
        }
    } 

    @BeforeEach
    void setup() {
        this.lfu1 = new LFU(SIZE_LFU);
        this.tlb1 = new TLB(lfu1);
        this.entries = new TlbEntry[QTD_PAIR];
        for (int i = 0; i < QTD_PAIR; i++) {
            this.entries[i] = new TlbEntry(i, i*2);
        }
    }

    @DisplayName("Testando logica da LFU")
    @Test
    void testandoLogicaDaLFU() throws MissInterruption {
        this.preencheTLB(tlb1, entries, SIZE_LFU);
        for (int i = 0; i < 3; i++) {
            this.tlb1.mapearPagina(i);
            this.tlb1.mapearPagina(i + 1);
        }
        this.tlb1.addPaginaMapeada(50, 100);
        assertAll(
            () -> assertThrows(MissInterruption.class, () -> this.tlb1.mapearPagina(4)),
            () -> assertEquals(6, tlb1.getHit()),
            () -> assertEquals(1, tlb1.getMiss())
        );
        this.tlb1.addPaginaMapeada(51, 101);
        assertAll(
            () -> assertThrows(MissInterruption.class, () -> this.tlb1.mapearPagina(50)),
            () -> assertEquals(6, tlb1.getHit()),
            () -> assertEquals(2, tlb1.getMiss())
        );
    }
    
}

