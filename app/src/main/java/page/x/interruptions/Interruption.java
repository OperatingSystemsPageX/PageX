package page.x.interruptions;

import page.x.Maquina;

public abstract class Interruption extends Exception {
    
    public Interruption(String string) {
        super(string);
    }

    public abstract void processar(Maquina maquina);    
}
