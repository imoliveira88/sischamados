package beans;

import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Pessoa;
import servico.PessoaServico;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable{
    private String nip;
    private String senha;
    private String nome;
    
    public LoginBean(){};

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
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

    //Compara se o nip digitado corresponde a um usuário válido, e, correspondendo,
    //compara a senha fornecida, com a senha que há no banco
    public boolean validaUsuario()throws SQLException{
        PessoaServico ud = new PessoaServico();
        try {
            String senhaRetornada = ud.retornaPessoa(this.nip).getSenha();
            return this.senha.equals(senhaRetornada);
        } catch (Exception e) {
            return false;
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
    
    public String doLogin() throws FacesException,ExceptionInInitializerError,SQLException{
        boolean valido;
        char tipo;
        Pessoa usu;
        
        try {
            valido = this.validaUsuario();

            if (!valido) {
                adicionaMensagem("Login ou senha incorretos!", "destinoAviso");
                return "login";
            } else {
                PessoaServico ud = new PessoaServico();
                usu = ud.retornaPessoa(this.nip);

                tipo = usu.getTipo();
                this.nome = usu.getNome();
                adicionaMensagem("Bem vindo, " + this.nome + "!", "destinoAviso");
                if (tipo == 'A') {
                    return "cadastros";
                } else {
                    return "criarChamado";
                }
            }
        } catch (Exception e) {
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
