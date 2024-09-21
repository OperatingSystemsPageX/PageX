package page.x.utils;

import java.util.Random;

public class MathUtils {
    public static Integer PegarNumeroAleatorio(Integer valorMinimo, Integer valorMaximo) {
        Random random = new Random();

        // Gera um número Integer aleatório entre valorMinimo e valorMaximo
        Integer pegarNumeroAleatorio = valorMinimo + random.nextInt((valorMaximo - valorMinimo) + 1);

        return pegarNumeroAleatorio;
    }
}
