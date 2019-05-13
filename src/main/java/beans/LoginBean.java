package beans;

import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.Divisao;
import modelo.Pessoa;
import servico.PessoaServico;

@SessionScoped
@Named("loginBean")
public class LoginBean implements Serializable{
    private String nip;
    private String senha;
    private String nome;
    private Divisao divisao;
    private Pessoa pessoaRetornada;
    
    public LoginBean(){
        divisao = new Divisao();
        pessoaRetornada = new Pessoa();
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Pessoa getPessoaRetornada() {
        return pessoaRetornada;
    }

    public void setPessoaRetornada(Pessoa pessoaRetornada) {
        this.pessoaRetornada = pessoaRetornada;
    }
    
    

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Divisao getDivisao() {
        return divisao;
    }

    public void setDivisao(Divisao divisao) {
        this.divisao = divisao;
    }
    
    

    //Compara se o nip digitado corresponde a um usuário válido, e, correspondendo,
    //compara a senha fornecida, com a senha que há no banco
    public boolean validaUsuario()throws SQLException{
        PessoaServico ud = new PessoaServico();
        try {
            pessoaRetornada = ud.retornaPessoa(this.nip);
            String senhaRetornada = pessoaRetornada.getSenha();
            return this.senha.equals(senhaRetornada);
        }  catch (Exception e) {
             return  false;
        }
    }
    
    public Pessoa retornaUsuario()throws SQLException{
        PessoaServico ud = new PessoaServico();
        try {
            Pessoa usu = ud.retornaPessoa(this.nip);
            return usu;
        } catch (Exception e) {
            return null;
        }
    }
    
    public  String  doLogin()  throws  FacesException,ExceptionInInitializerError,SQLException{
        boolean valido;
        char tipo;
        Pessoa  usu;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        
         try {
            valido =  this.validaUsuario();

            if (!valido) {
                 adicionaMensagem("Login ou senha incorretos!", "destinoAviso");
                 return "login";
            }  else {

                tipo =  pessoaRetornada.getTipo();
                
                 this.nome =  pessoaRetornada.getNome();
                 adicionaMensagem("Bem vindo, " +  this.nome + "!", "destinoAviso");
                 session.setAttribute("logado", "sim");
                if (tipo == 'A') {
                     return "admin/cadPessoa.xhtml?faces-redirect=true";
                }  else {
                     this.divisao =  pessoaRetornada.getDivisao();
                     return "logado/usuario/chamadosParaDivisao.xhtml?faces-redirect=true";
                }
            }
        }  catch (Exception e) {
             adicionaMensagem("Login ou senha incorretos!", "destinoAviso");
             return "login";
        }
   
      }

    
    private void adicionaMensagem(String mensagem, String destino){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        msg = new FacesMessage("",mensagem);
        context.addMessage(destino, msg);
    }

}

/*
- Usuário que criou chamado pode excluí-lo (ou por ter resolvido, ou por ter errado algo)
- Possibilidade de gerar relatórios entre datas
*/
