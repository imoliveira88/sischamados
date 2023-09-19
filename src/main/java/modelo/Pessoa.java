package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@NamedQueries(value = 
         {@NamedQuery(name = "Pessoa.retornaPessoasDivisao", query= " SELECT u FROM Pessoa u WHERE u.divisao = :divisao"),
          @NamedQuery(name = "Pessoa.retornaQtdPessoasDivisao", query= " SELECT COUNT(u) FROM Pessoa u WHERE u.divisao = :divisao")})
@Table(name = "TB_PESSOA")
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Pessoa(String nome, String senha, String nip, String especialidade){
        this.nome = nome;
        this.senha = senha;
        this.login = nip;
        this.funcao = especialidade;
        this.chamados = new ArrayList<>();
    }
    
    public Pessoa(){   
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PESSOA")
    private Long id;
    
    @OneToMany(mappedBy = "solicitante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Chamado> chamados;
    
    @ManyToOne
    @JoinColumn(name = "ID_DIVISAO", referencedColumnName = "ID_DIVISAO")
    private Divisao divisao;
    
    @NotNull
    @Column(name = "TIPO")
    private char tipo; //Se tipo='A', administrador, quem cadastra divisões, usuários, etc, se tipo='U', pessoa normal
    
    @Size(min = 2, max = 40)
    @NotNull
    @Column(name = "NOME")
    private String nome;
    
    @Column(name = "TELEFONE")
    private String telefone;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "FUNCAO")
    private String funcao;
    
    @NotBlank
    @Column(name = "SENHA")
    private String senha;
    
    @NotBlank
    @Column(name = "LOGIN")
    private String login;
    
    public void addChamado(Chamado chamado) {
        chamados.add(chamado);
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Divisao getDivisao() {
        return divisao;
    }

    public void setDivisao(Divisao divisao) {
        this.divisao = divisao;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public String toString(){
        String aux = "";
        if(this.funcao != null) aux += this.funcao + " ";
        return  aux + this.nome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    public boolean equals(Pessoa usu) {
        return this.login.equals(usu.login);
    }
    
}
