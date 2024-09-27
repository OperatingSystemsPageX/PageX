package page.x.TLB.algoritmos.substituicao;

import page.x.interruptions.MissInterruption;

public interface AlgoritmoSubstituicaoI {
    public Long mapearPagina(Long vpn) throws MissInterruption;

    public void addPaginaMapeada(Long vpn, Long pfn);

    public String nomeToString();

    public int getQtdEntries();
}
