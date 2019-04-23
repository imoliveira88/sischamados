/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import modelo.Chamado;
import modelo.Divisao;
import modelo.Pessoa;
import servico.PessoaServico;

@SessionScoped
@Named("pessoaMB")
public class PessoaMB extends Artificial implements Serializable{
    
    private List<Chamado> chamados;
    private Divisao divisao;
    private char tipo; //Se tipo='A', administrador, quem cadastra divisões, usuários, etc, se tipo='U', pessoa normal
    private String nome;
    private String posto;
    private String especialidade;
    private boolean militar;//no front-end implementar a aquisição uma drop-down com "militar" e "civil" como opções
    private String senha;
    private String nip;

    public PessoaMB() {
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    public Divisao getDivisao() {
        return divisao;
    }

    public void setDivisao(Divisao divisao) {
        this.divisao = divisao;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public boolean getMilitar() {
        return militar;
    }

    public void setMilitar(boolean militar) {
        this.militar = militar;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
    
    public List<Pessoa> getPessoas(){
        return (new PessoaServico()).pessoas();
    }
    
    public String cadastraUsuario() throws ParseException{
        PessoaServico cli = new PessoaServico();
        
        try {
            Pessoa pes = new Pessoa(nome,senha,nip,militar,especialidade,posto);
            pes.setTipo('U'); 
            
            if (cli.salvar(pes)) {
                this.adicionaMensagem("Cadastro feito com sucesso!","destinoAviso");
                return "cadPessoa";
            } else {
                this.adicionaMensagem("Já existe um usuário com este nip!","destinoAviso");
                return "cadPessoa";
            }
        } catch (Exception e) {
            this.adicionaMensagem("Houve um erro no cadastro! Tente novamente!","destinoAviso");
            return "cadPessoa";
        }  
    }
    
    public String excluiUsuario(Long id) throws ParseException{
        PessoaServico cli = new PessoaServico();
        
        try {
            cli.excluir(id);
            this.adicionaMensagem("Usuário removido!","destinoAviso");
            return "cadPessoa";
        } catch (Exception e) {
            this.adicionaMensagem("Houve um erro no cadastro! Tente novamente!","destinoAviso");
            return "cadPessoa";
        }  
    }
    
}
