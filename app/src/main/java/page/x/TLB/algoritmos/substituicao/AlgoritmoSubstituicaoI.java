package page.x.TLB.algoritmos.substituicao;

import page.x.interruptions.MissInterruption;

public interface AlgoritmoSubstituicaoI<T> {
    public T acessEntry(Long accessID) throws MissInterruption;

    public void addEntry(T entry);

    public String nomeToString();

    public int getQtdEntries();

    public void reset();
}
