package page.x.interruptions;

import page.x.estados.EnderecoVirtual;

public class MissInterruption extends Exception {
    
    private EnderecoVirtual enderecoVirtual;

    public MissInterruption() {
        super("MISS");
        this.enderecoVirtual = null;
    }

    public MissInterruption(EnderecoVirtual enderecoVirtual) {
        super("MISS");
        this.enderecoVirtual = enderecoVirtual;
    }

    public EnderecoVirtual getEnderecoVirtual() {
        return this.enderecoVirtual;
    }

}
