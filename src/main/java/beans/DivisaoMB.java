/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import modelo.Divisao;
import servico.DivisaoServico;

/**
 *
 * @author usuario
 */
@RequestScoped
@Named("divisaoMB")
public class DivisaoMB extends Artificial implements Serializable{
    
    private Divisao divisao;
    private String nome;
    private int numero;

    public DivisaoMB() {
        divisao = new Divisao();
    }

    public Divisao getDivisao() {
        return divisao;
    }

    public void setDivisao(Divisao divisao) {
        this.divisao = divisao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    

    public String salvar() {
        DivisaoServico pra = new DivisaoServico();
        divisao.setNome(nome);
        divisao.setNumero(numero);
        if(!pra.existeDivisao(this.divisao)){
            pra.salvar(divisao);
            adicionaMensagem("Divisão cadastrada com sucesso!","destinoAviso");
            return "cadDivisao";
        }
        else{
            adicionaMensagem("Erro! A divisão já está cadastrada!","destinoAviso");
            return "cadDivisao";
        }
    }
    
    public List<Divisao> getDivisoes(){
        return new DivisaoServico().todasDivisoes();
    }
    
    public String excluir(Long id) throws Exception{
        DivisaoServico pra = new DivisaoServico();
        if(pra.deletarDivisao(id)){
            adicionaMensagem("Divisão removida com sucesso!","destinoAviso");
        }else{
            adicionaMensagem("Divisão não pode ser removida, pois há colaboradores cadastrados nela! Caso queira excluí-la atualize primeiro as informações de seus colaboradores e, então, tente novamente!","destinoAviso");
        }
        return "divisao";
    }
    
    
}
