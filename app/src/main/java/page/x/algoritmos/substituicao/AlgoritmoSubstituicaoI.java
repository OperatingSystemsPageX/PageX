package page.x.algoritmos.substituicao;

import page.x.interruptions.MissInterruption;

public interface AlgoritmoSubstituicaoI {
    public Integer mapearPagina(Integer vpn) throws MissInterruption;

    public void addPaginaMapeada(Integer vpn, Integer pfn);
}
