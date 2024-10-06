package page.x.TLB.algoritmos.substituicao;

import page.x.interruptions.MissInterruption;

public interface AlgoritmoSubstituicaoI<T> {
    public T acessEntry(Long accessID) throws MissInterruption;

    public T addEntry(T entry);

    public String nomeToString();

    public Long getQtdEntries();

    public void reset();
}
