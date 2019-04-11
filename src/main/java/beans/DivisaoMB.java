/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Divisao;
import servico.DivisaoServico;

/**
 *
 * @author usuario
 */
@ManagedBean(name = "divisaoMB")
@SessionScoped
public class DivisaoMB extends Artificial{
    
    private Divisao divisao;

    public DivisaoMB() {
    }

    public Divisao getDivisao() {
        return divisao;
    }

    public void setDivisao(Divisao divisao) {
        this.divisao = divisao;
    }

    public String salvar() {
        DivisaoServico pra = new DivisaoServico();
        if(!pra.existeDivisao(this.divisao)){
            pra.save(divisao);
            adicionaMensagem("Divisão cadastrada com sucesso!","destinoAviso");
            return "divisao";
        }
        else{
            adicionaMensagem("Erro! A divisão já está cadastrada!","destinoAviso");
            return "divisao";
        }
    }
    
    public String excluir(Divisao pr) throws Exception{
        DivisaoServico pra = new DivisaoServico();
        if(pra.deletarDivisao(pr)){
            adicionaMensagem("Divisão removida com sucesso!","destinoAviso");
        }else{
            adicionaMensagem("Divisão não pode ser removido, pois há colaboradores cadastrados nela! Caso queira excluí-la atualize primeiro as informações de seus colaboradores e, então, tente novamente!","destinoAviso");
        }
        return "divisao";
    }
    
    
}
