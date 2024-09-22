package page.x.utils;

import java.util.Random;

import page.x.memoriafisica.PageFrame;

import java.util.HashMap;

public class Sorteador {
    private Random random;
    private Long qtdBits;

    public Sorteador(Long qtdBits) {
        this.random = new Random();
        this.qtdBits = qtdBits;
    }

    public Long sortearNumero(HashMap<Long, PageFrame> sorteados) {
        StringBuilder binaryString = new StringBuilder();

        for (int i = 0; i < qtdBits; i++) {
            int bit = random.nextInt(2);
            binaryString.append(bit);
        }

        Long numeroAleatorio = Long.parseLong(binaryString.toString(), 2);

        // O limite máximo com a quantidade de bits sorteados (2 ^ qtdBits - 1)
        Long limite = (long) (Math.pow(2, qtdBits) - 1);

        while (sorteados.containsKey(numeroAleatorio)) {
            numeroAleatorio = (numeroAleatorio + 1) & limite;
        }

        return numeroAleatorio;
    }

    public Long getQtdBits() {
        return qtdBits;
    }
}