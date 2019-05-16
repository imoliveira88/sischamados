
package beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Query;
import modelo.Chamado;
import modelo.Divisao;
import modelo.Pessoa;
import servico.DivisaoServico;
import servico.PessoaServico;

@RequestScoped
@Named("pessoaMB")
public class PessoaMB extends Artificial implements Serializable{
    
    private List<Chamado> chamados;
    private String divisao;
    private char tipo; //Se tipo='A', administrador, quem cadastra divisões, usuários, etc, se tipo='U', pessoa normal
    private String nome;
    private String posto;
    private String especialidade;
    private boolean militar;//no front-end implementar a aquisição uma drop-down com "militar" e "civil" como opções
    private String senha;
    private String nip;
    private String email;
    private String telefone;
    private Pessoa pessoaSelecionada;

    public PessoaMB() {
        chamados = new ArrayList<>();
        pessoaSelecionada = new Pessoa();
        //divisao = new Divisao();
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    public String getDivisao() {
        return divisao;
    }

    public void setDivisao(String divisao) {
        this.divisao = divisao;
    }
    
    

    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
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
    
    //Senha de no mínimo 6 e no máximo 15 caracteres, tendo pelo menos uma minúscula, uma maiúscula, um número e um caractere especial
    public boolean validaSenha(String senha) {
        boolean min, mai, num, car;
        min = mai = num = car = false;
        char atual;
        if (senha.length() < 6 || senha.length() > 15) {
            return false;
        }
        for (int i = 0; i < senha.length(); i++) {
                atual = senha.charAt(i);
                if (Character.isLowerCase(atual))min = true;
                if (Character.isUpperCase(atual)) mai = true;
                if (Character.isDigit(atual)) num = true;
                if ((atual > 32 && atual < 48) || (atual > 57 && atual < 65) || (atual > 90 && atual < 97)) car = true;
        }
        return min && mai && num && car;
    }
    
    //Implementação do salvamento do hash da senha, para futura comparação
    public String hash(String texto) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));

        String hex = String.format("%064x", new BigInteger(1, hash));
        
        return hex;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }


    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    
    
    public List<Pessoa> getPessoas(){
        return (new PessoaServico()).pessoas();
    }
    
    public String cadastraUsuario() throws ParseException, SQLException{
        PessoaServico cli = new PessoaServico();
        Divisao div;
                
        try {
            if(!this.validaSenha(this.senha)){
                this.adicionaMensagem("A senha escolhida não atende os requisitos mínimos de segurança! A senha deve ter entre 6 e 15 caracteres, conter letras minúsculas, maiúsculas, números e caracteres especiais!","destinoAviso");
                return "cadPessoa";
            }
            Pessoa pes = new Pessoa(nome.toUpperCase(),this.hash(senha),nip,militar,especialidade.toUpperCase(),posto.toUpperCase());
            pes.setTipo('U'); 
            pes.setEmail(email.toLowerCase());
            pes.setTelefone(telefone);
            
            div = (new DivisaoServico()).retornaDivisao(divisao);
            pes.setDivisao(div);
          
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
    
    public List<Pessoa> retornaPessoasDivisao(Divisao d){
        return (new PessoaServico()).pessoasDivisao(d);
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