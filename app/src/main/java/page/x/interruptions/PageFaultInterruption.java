package page.x.interruptions;

import page.x.Maquina;

public class PageFaultInterruption extends Interruption {

    public PageFaultInterruption() {
        super("PAGE FAULT");
    }

    @Override
    public void processar(Maquina maquina) {
        System.out.println("UM PAGEFAULT FOI GERADO");
        maquina.getEstado().avancaEstado();
    }
}