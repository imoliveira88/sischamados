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
        {@NamedQuery(name = "Pessoa.retornaPessoa", query= " SELECT u FROM Pessoa u WHERE u.nip = :nip")})
@Table(name = "TB_PESSOA")
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Pessoa(String nome, String senha, String nip, boolean militar, String especialidade, String posto){
        this.nome = nome;
        this.senha = senha;
        this.nip = nip;
        this.militar = militar;
        this.especialidade = especialidade;
        this.posto = posto;
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
    
    //@NotNull
    @ManyToOne
    @JoinColumn(name = "ID_DIVISAO", referencedColumnName = "ID_DIVISAO")
    private Divisao divisao;
    
    @NotNull
    @Column(name = "tipo")
    private char tipo; //Se tipo='A', administrador, quem cadastra divisões, usuários, etc, se tipo='U', pessoa normal
    
    @Size(min = 2, max = 40)
    @NotNull
    @Column(name = "NOME")
    private String nome;
    
    @NotNull
    @Column(name = "POSTO")
    private String posto;
    
    @NotNull
    @Column(name = "ESPECIALIDADE")
    private String especialidade;
    
    @NotNull
    @Column(name = "MILITAR")
    private boolean militar;//no front-end implementar a aquisição uma drop-down com "militar" e "civil" como opções
    
    @NotBlank
    @Column(name = "SENHA")
    private String senha;
    
    @NotBlank
    @Column(name = "DOCUMENTO")
    private String nip;
    
    public void addChamado(Chamado chamado) {
        chamados.add(chamado);
    }

    public String getDocumento() {
        return nip;
    }

    public void setDocumento(String documento) {
        this.nip = documento;
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

    public Divisao getDivisao() {
        return divisao;
    }

    public void setDivisao(Divisao divisao) {
        this.divisao = divisao;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
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
        String s = "";
        
        s += "Nome: " + this.getNome();
        
        return s;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    public boolean equals(Pessoa usu) {
        return this.nip.equals(usu.nip);
    }
    
}
