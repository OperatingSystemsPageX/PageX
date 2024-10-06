package page.x.interruptions;

import page.x.Maquina;

public class InterruptHandler {
    
    private Maquina maquina;

    public InterruptHandler(Maquina maquina) {
        this.maquina = maquina;
    }

    public void handleInterruption(Interruption interruption) {
        interruption.processar(this.maquina);
    }
}
