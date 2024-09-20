package page.x.algoritmos.substituicao;

import java.util.LinkedList;

import page.x.interruptions.MissInterruption;
import page.x.utils.Pair;

public class LRU implements AlgoritmoSubstituicaoI {
    
    private LinkedList<Pair<Integer, Integer>> paginas;

    private int capacidade;

    public LRU(int capacidade) {
        this.capacidade = capacidade;
        this.paginas = new LinkedList<Pair<Integer, Integer>>();
    }

    @Override
    public Integer mapearPagina(Integer page) throws MissInterruption {
        for (Pair<Integer, Integer> pageAtual : this.paginas) {
            if (pageAtual.getPair1().equals(page)) {
                this.execucaoAlgoritmo(pageAtual);
                return pageAtual.getPair2();
            }
        }
        throw new MissInterruption();
    }

    private void execucaoAlgoritmo(Pair<Integer, Integer> substituir) {
        this.paginas.remove(substituir);
        this.paginas.addLast(substituir);
    }    

    @Override
    public void addPaginaMapeada(Pair<Integer, Integer> page) {
        if (capacidade == this.paginas.size()) {
            this.paginas.removeFirst();
        } 
        this.paginas.addLast(page);
    }

}
