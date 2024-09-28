package page.x.interruptions;

import page.x.Maquina;

public class MissInterruption extends Interruption {
    
    public MissInterruption() {
        super("MISS");
    }

    @Override
    public void processar(Maquina maquina) {
        System.out.println("UM MISS FOI GERADO");
        maquina.getEstado().avancaEstado();
    }

}
