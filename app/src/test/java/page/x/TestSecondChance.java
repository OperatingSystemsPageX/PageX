package page.x;

import org.junit.jupiter.api.Test;

import page.x.algoritmos.substituicao.SecondChance;
import page.x.interruptions.MissInterruption;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

class TestSecondChance {

    private final static int SIZE_SC = 5;
    private final static int QTD_PAIR = 10;
    private SecondChance sc1;
    private TLB tlb1;
    private TlbEntry[] entries;
    
    void preencheTLB(TLB tlb, TlbEntry[] entries, Integer size) throws MissInterruption{
        for (int i = 0; i < size; i++) {
            tlb.addPaginaMapeada(entries[i].getVirtualPageNumber(), entries[i].getPageFrameNumber());
        }
    } 

    @BeforeEach
    void setup() {
        this.sc1 = new SecondChance(SIZE_SC);
        this.tlb1 = new TLB(sc1);
        this.entries = new TlbEntry[QTD_PAIR];
        for (int i = 0; i < QTD_PAIR; i++) {
            this.entries[i] = new TlbEntry(i, i*2);
        }
    }

    @DisplayName("Testando logica do SecondChance")
    @Test
    void testandoLogicaDaSecondChance() throws MissInterruption {
        this.preencheTLB(tlb1, entries, SIZE_SC);
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
            () -> assertThrows(MissInterruption.class, () -> this.tlb1.mapearPagina(0)),
            () -> assertEquals(6, tlb1.getHit()),
            () -> assertEquals(2, tlb1.getMiss())
        );
    }
    
}

