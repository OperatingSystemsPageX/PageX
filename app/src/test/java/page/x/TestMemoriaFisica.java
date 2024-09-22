package page.x;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import page.x.interruptions.FullPhysicalMemoryInterruption;
import page.x.memoriafisica.MemoriaFisica;

class TestMemoriaFisica {

    private MemoriaFisica memoriaFisica;
    private final static Long TAMANHO_DA_MAQUINA_EM_BITS = 8L; // Exemplo: 256 bytes de memória
    private final static Long TAMANHO_DA_PAGE_EM_BITS = 4L;    // Exemplo: 16 bytes por página
    private final static Long QUANTIDADE_DE_PAGE_FRAMES = (1L << (TAMANHO_DA_MAQUINA_EM_BITS - TAMANHO_DA_PAGE_EM_BITS)); // Número máximo de frames

    @BeforeEach
    void setup() {
        this.memoriaFisica = new MemoriaFisica(TAMANHO_DA_MAQUINA_EM_BITS, TAMANHO_DA_PAGE_EM_BITS);
    }

    @Test
    @DisplayName("Alocar PageFrame corretamente")
    void alocarPageFrameCorretamente() throws FullPhysicalMemoryInterruption {
        Long pageFrame = this.memoriaFisica.alocarPageFrame();
        assertNotNull(pageFrame, "PageFrame não deveria ser nulo após a alocação");
        assertTrue(pageFrame >= 0 && pageFrame < QUANTIDADE_DE_PAGE_FRAMES, 
            "PageFrame alocado deve estar dentro dos limites da quantidade de PageFrames");
    }

    @Test
    @DisplayName("Verificar se todos PageFrames podem ser alocados sem repetição")
    void alocarTodosPageFrames() throws FullPhysicalMemoryInterruption {
        for (int i = 0; i < QUANTIDADE_DE_PAGE_FRAMES; i++) {
            Long pageFrame = this.memoriaFisica.alocarPageFrame();
            assertNotNull(pageFrame, "PageFrame não deveria ser nulo após a alocação");
        }
        // Verifica se a alocação atingiu o limite sem erros
        assertEquals(QUANTIDADE_DE_PAGE_FRAMES, memoriaFisica.getUtilizacaoMemoriaFisica().longValue(),
            "A memória física deve conter todos os PageFrames alocados");
    }

    @Test
    @DisplayName("Não deve alocar mais PageFrames do que a capacidade máxima")
    void naoDeveAlocarMaisQueCapacidade() throws FullPhysicalMemoryInterruption {
        for (int i = 0; i < QUANTIDADE_DE_PAGE_FRAMES; i++) {
            this.memoriaFisica.alocarPageFrame();
        }
        // Tentando alocar mais do que o permitido
        assertThrows(FullPhysicalMemoryInterruption.class, () -> {
            this.memoriaFisica.alocarPageFrame();
        }, "Deve lançar uma exceção se tentar alocar mais PageFrames do que a capacidade");
    }
}