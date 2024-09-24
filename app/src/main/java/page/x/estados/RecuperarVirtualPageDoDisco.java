/* Recuperar o conteúdo da página no disco:
    - Adiciona na memória fisica um page frame aleatório e mapear na page table
    - Vai para o estado que atualiza o bit valido(é necessário no nosso contexto?)
*/
package page.x.estados;

import page.x.Maquina;

public class RecuperarVirtualPageDoDisco  implements TraducaoState {
    private Maquina maquina;
    private EnderecoVirtual enderecoVirtual;

    public RecuperarVirtualPageDoDisco(Maquina maquina, EnderecoVirtual enderecoVirtual) {
        this.maquina = maquina;
        this.enderecoVirtual = enderecoVirtual;
    }
    
    @Override
    public void efetuarOperacao() {
        TraducaoState proximoEstado = new AtualizarBitValido(maquina, enderecoVirtual);
        maquina.setTraducaoState(proximoEstado);
        maquina.avancarEstado(); 
    }
}