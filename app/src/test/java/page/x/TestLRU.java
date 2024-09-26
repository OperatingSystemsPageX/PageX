package page.x;

import org.junit.jupiter.api.Test;

import page.x.TLB.TLB;
import page.x.TLB.TlbEntry;
import page.x.TLB.algoritmos.substituicao.LRU;
import page.x.interruptions.MissInterruption;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

class TestLRU {

    private final static int SIZE_LRU = 5;
    private final static int QTD_PAIR = 10;
    private LRU lru1;
    private TLB tlb1;
    private TlbEntry[] entries;
    
    void preencheTLB(TLB tlb, TlbEntry[] entries, Integer size) throws MissInterruption{
        for (int i = 0; i < size; i++) {
            tlb.addPaginaMapeada(entries[i].getVirtualPageNumber(), entries[i].getPageFrameNumber());
        }
    } 

    @BeforeEach
    void setup() {
        this.lru1 = new LRU(SIZE_LRU);
        this.tlb1 = new TLB(lru1, SIZE_LRU) ;
        this.entries = new TlbEntry[QTD_PAIR];
        for (int i = 0; i < QTD_PAIR; i++) {
            this.entries[i] = new TlbEntry(i, i*2);
        }
    }
    
    @Test
    @DisplayName("Preenchendo a TLB e verificando o HIT")
    void preenchendoTLBeVerificandoHIT() throws MissInterruption {
        this.preencheTLB(this.tlb1, this.entries, SIZE_LRU);
        for (int i = 0; i < SIZE_LRU; i++) {
            this.tlb1.mapearPagina(i);
        }
        assertAll(
            () -> assertEquals(0, tlb1.getMiss()),
            () -> assertEquals(5, tlb1.getHit())
        );
    }

    @Test
    @DisplayName("Preenchendo a TLB e verificando o MISS")
    void preenchendoTLBeVerificandoMISS() throws MissInterruption {
        this.preencheTLB(this.tlb1, this.entries, SIZE_LRU);
        for (int i = 0; i < QTD_PAIR; i++) {
            try {
                this.tlb1.mapearPagina(i);
            } catch (MissInterruption msg) {}
        }
        assertAll(
            () -> assertEquals(5, tlb1.getMiss()),
            () -> assertEquals(5, tlb1.getHit())
        );
    }

    @Test
    @DisplayName("Verificando a lógica da LRU-01")
    void preenchendoTLBeVerificandoLRU01() throws MissInterruption {
        this.preencheTLB(this.tlb1, this.entries, SIZE_LRU);
        this.tlb1.mapearPagina(0);
        this.tlb1.mapearPagina(4);

        assertThrows(MissInterruption.class, () -> tlb1.mapearPagina(10000));
    
        this.tlb1.addPaginaMapeada(10000, 10001);
            
        assertThrows(MissInterruption.class, () -> tlb1.mapearPagina(1));
        
        assertAll(
            () -> assertEquals(2, tlb1.getMiss()),
            () -> assertEquals(2, tlb1.getHit())
        );
    }

    @Test
    @DisplayName("Verificando a lógica da LRU-02")
    void preenchendoTLBeVerificandoLRU02() throws MissInterruption {
        this.preencheTLB(this.tlb1, this.entries, SIZE_LRU);
        for (int i = SIZE_LRU; i < QTD_PAIR-1; i++) {
            this.tlb1.addPaginaMapeada(entries[i].getVirtualPageNumber(), entries[i].getPageFrameNumber());
        }
        this.tlb1.mapearPagina(5);
        assertAll(
            () -> assertEquals(0, tlb1.getMiss()),
            () -> assertEquals(1, tlb1.getHit())
        );
        this.tlb1.addPaginaMapeada(entries[5].getVirtualPageNumber(), entries[5].getVirtualPageNumber());
        try {
            this.tlb1.mapearPagina(1);
        } catch (Exception e) {}
        assertAll(
            () -> assertEquals(1, tlb1.getMiss()),
            () -> assertEquals(1, tlb1.getHit())
        );
    }
    
    @Test
    @DisplayName("Tentando mapear com TLB Vazia")
    void tentandoMapearComTLBVazia() throws MissInterruption {
        assertThrows(MissInterruption.class, () -> tlb1.mapearPagina(1));
        assertAll(
            () -> assertEquals(1, tlb1.getMiss()),
            () -> assertEquals(0, tlb1.getHit())
        );
    }
    
}

