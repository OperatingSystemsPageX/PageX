package page.x;

import org.junit.jupiter.api.Test;

import page.x.algoritmos.substituicao.LRU;
import page.x.interruptions.MissInterruption;
import page.x.utils.Pair;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

class TestTLB {

    private final static int SIZE_LRU = 5;
    private final static int QTD_PAIR = 10;
    private LRU lru1;
    private TLB tlb1;
    private Pair<Integer, Integer>[] pairs;
    
    void preencheTLB(TLB tlb, Pair<Integer, Integer>[] pairs, Integer size) throws MissInterruption{
        for (int i = 0; i < size; i++) {
            tlb.addPaginaMapeada(pairs[i]);
        }
    } 

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setup() {
        this.lru1 = new LRU(SIZE_LRU);
        this.tlb1 = new TLB(lru1);
        this.pairs = (Pair<Integer, Integer>[]) new Pair[QTD_PAIR];
        for (int i = 0; i < QTD_PAIR; i++) {
            this.pairs[i] = new Pair<Integer,Integer>(i, i*2);
        }
    }
    
    @Test
    @DisplayName("Preenchendo a TLB e verificando o HIT")
    void preenchendoTLBeVerificandoHIT() throws MissInterruption {
        this.preencheTLB(this.tlb1, this.pairs, SIZE_LRU);
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
        this.preencheTLB(this.tlb1, this.pairs, SIZE_LRU);
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
        this.preencheTLB(this.tlb1, this.pairs, SIZE_LRU);
        this.tlb1.mapearPagina(0);
        this.tlb1.mapearPagina(4);
        try {
            this.tlb1.mapearPagina(10000);
        } catch (MissInterruption msg) {}
        this.tlb1.addPaginaMapeada(new Pair<Integer,Integer>(10000, 10001));
        try {
            this.tlb1.mapearPagina(1);
        } catch (MissInterruption msg) {}
        assertAll(
            () -> assertEquals(2, tlb1.getMiss()),
            () -> assertEquals(2, tlb1.getHit())
        );
    }

    @Test
    @DisplayName("Verificando a lógica da LRU-02")
    void preenchendoTLBeVerificandoLRU02() throws MissInterruption {
        this.preencheTLB(this.tlb1, this.pairs, SIZE_LRU);
        for (int i = SIZE_LRU; i < QTD_PAIR-1; i++) {
            this.tlb1.addPaginaMapeada(pairs[i]);
        }
        this.tlb1.mapearPagina(5);
        assertAll(
            () -> assertEquals(0, tlb1.getMiss()),
            () -> assertEquals(1, tlb1.getHit())
        );
        this.tlb1.addPaginaMapeada(pairs[5]);
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
        try {
            this.tlb1.mapearPagina(1);
        } catch (MissInterruption msg) {
            assertAll(
                () -> assertEquals(1, tlb1.getMiss()),
                () -> assertEquals(0, tlb1.getHit()),
                () -> assertEquals("MISS", msg.getMessage())
            );
        }

    }
    
}
