package page.x;

import page.x.interruptions.FullPhysicalMemoryInterruption;
import page.x.memoriafisica.MemoriaFisica;

public class PageX {

    public static void main(String[] args) {
        System.out.println("Welcome to PageX!");

        MemoriaFisica memoriaFisica = new MemoriaFisica(22L, 12L);
        int i = 0;
        while (i++ <= 1023) {
            try {
                System.out.println(memoriaFisica.alocarPageFrame());
            } catch (FullPhysicalMemoryInterruption e) {
                e.printStackTrace();
            }   
        }
    }
}
