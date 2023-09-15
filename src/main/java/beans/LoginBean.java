package beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.FacesException;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import modelo.Divisao;
import modelo.Pessoa;
import servico.DAOGenericoJPA;
import servico.PessoaServico;

/**
 *
 * @author magalhaes
 */
@SessionScoped
@ManagedBean(name = "loginBean")
public class LoginBean extends Artificial implements Serializable {

    private String nip;
    private String senha;
    private String nome;
    private Divisao divisao;
    private Pessoa pessoaRetornada;
    private String senhaNova;

    public LoginBean() {
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

    public int getNumeroSessoes() {
        return SessionListener.getActiveSessionCount();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
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

    public String hash(String texto) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));

        String hex = String.format("%064x", new BigInteger(1, hash));

        return hex;
    }

    //Compara se o nip digitado corresponde a um usuário válido, e, correspondendo,
    //compara o hash da senha fornecida, com a senha que há no banco
    //Faz verificação de quantas vezes o usuário tentou fazer login
    public boolean validaUsuario() throws SQLException {
        PessoaServico ud = new PessoaServico();

        try {
            pessoaRetornada = ud.retornaPessoa("nip", this.nip);
            String senhaRetornada = pessoaRetornada.getSenha();
            System.out.println("PPPPPessoa: " + pessoaRetornada.getNome());
            if(pessoaRetornada.getNip().equals("admin")){
                return senhaRetornada.equals(this.senha);
            }
            return this.hash(this.senha).equals(senhaRetornada) || this.senha.equals(senhaRetornada); //abrangendo o caso do Admin visitante
        } catch (Exception e) {
            return false;
        }
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
            if (Character.isLowerCase(atual)) {
                min = true;
            }
            if (Character.isUpperCase(atual)) {
                mai = true;
            }
            if (Character.isDigit(atual)) {
                num = true;
            }
            if ((atual > 32 && atual < 48) || (atual > 57 && atual < 65) || (atual > 90 && atual < 97)) {
                car = true;
            }
        }
        return min && mai && num && car;
    }

    public String alterarSenha() throws NoSuchAlgorithmException, Exception {
        if (!this.hash(this.senha).equals(pessoaRetornada.getSenha()) && !pessoaRetornada.getSenha().equals("aB123456@")) {
            this.adicionaMensagem("A senha digitada não corresponde à senha gravada no banco de dados!", "destinoAviso", "ERRO!");
            return "novaSenha.xhtml";
        }
        if (!this.validaSenha(this.senhaNova)) {
            this.adicionaMensagem("A senha escolhida não atende os requisitos mínimos de segurança! A senha deve ter entre 6 e 15 caractere, conter letras minúsculas, maiúsculas, números e caracteres especiais!", "destinoAviso", "ATENÇÃO!");
            return "novaSenha.xhtml";
        }
        PessoaServico ps = new PessoaServico();
        Query query = ps.getEm().createQuery("UPDATE Pessoa e SET e.senha = :senha WHERE e.id = :id");
        query.setParameter("senha", this.hash(this.senhaNova));
        query.setParameter("id", pessoaRetornada.getId());
        ps.getEm().getTransaction().begin();
        query.executeUpdate();
        ps.getEm().getTransaction().commit();
        this.adicionaMensagem("Senha alterada com sucesso! Faça login novamente!", "destinoAviso", "SUCESSO!");
        return "login";
    }

    public List<HttpSession> listaSessoes() {
        return SessionListener.sessoes;
    }

    public String doLogin() throws FacesException, ExceptionInInitializerError, SQLException, ParseException, UnknownHostException {
        boolean valido;
        char tipo;
        boolean prestadora;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session;

        try {
            valido = this.validaUsuario();

            if (!valido) {
                adicionaMensagem("Login ou senha incorretos!", "destinoAviso", "ERRO!");
                return "login.xhtml";
            } else {
                if (this.senha.equals("aB123456@") || this.senha.equals(this.hash("aB123456@"))) {
                    adicionaMensagem("Primeiro acesso? Altere sua senha!", "destinoAviso", "ATENÇÃO!");
                    return "novaSenha.xhtml";
                }
                tipo = pessoaRetornada.getTipo();

                this.nome = pessoaRetornada.getNome();
                adicionaMensagem("Bem vindo, " + pessoaRetornada.toString() + "!", "destinoAviso", "BEM VINDO!");
                session = (HttpSession) fc.getExternalContext().getSession(false);
                session.setAttribute("logado", "sim");
                session.setAttribute("usuario", pessoaRetornada);
                if (tipo == 'A') {
                    return "logado/admin/cadPessoa.xhtml";
                } else {
                    prestadora = pessoaRetornada.getDivisao().isPrestadora();
                    this.divisao = pessoaRetornada.getDivisao();
                    if (prestadora) {
                        return "logado/prestadora/chamadosParaDivisao.xhtml?faces-redirect=true";
                    } else {
                        return "logado/usuario/meusChamados.xhtml";
                    }
                }
            }
        } catch (Exception e) {
            adicionaMensagem("Login ou senha incorretos!", "destinoAviso", "ERRO!");
            return "login";
        }

    }

}
