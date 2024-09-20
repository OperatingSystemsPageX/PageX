package page.x.algoritmos.substituicao;

import page.x.interruptions.MissInterruption;
import page.x.utils.Pair;

public interface AlgoritmoSubstituicaoI {
    public Integer mapearPagina(Integer page) throws MissInterruption;

    public void addPaginaMapeada(Pair<Integer, Integer> page);
}
