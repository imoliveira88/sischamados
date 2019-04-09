package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@NamedQueries(value = 
        {@NamedQuery(name = "Usuario.RetornaUsuario", query= " SELECT u FROM Usuario u WHERE u.telefone = :tel")})
@Table(name = "TB_PESSOA")
public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Pessoa(String nome, String senha, String documento, boolean militar, String corpo, String posto){
        this.nome = nome;
        this.senha = senha;
        this.documento = documento;
        this.militar = militar;
        this.corpo = corpo;
        this.posto = posto;
    }
    
    public Pessoa(){};
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min = 2, max = 40)
    @NotNull
    @Column(name = "NOME")
    private String nome;
    
    @NotNull
    @Column(name = "POSTO")
    private String posto;
    
    @NotNull
    @Column(name = "CORPO")
    private String corpo;
    
    @NotNull
    @Column(name = "MILITAR")
    private boolean militar;//no front-end implementar a aquisição uma drop-down com "militar" e "civil" como opções
    
    @NotBlank
    @Column(name = "SENHA")
    private String senha;
    
    @NotBlank
    @Column(name = "DOCUMENTO")
    private String documento;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public boolean isMilitar() {
        return militar;
    }

    public void setMilitar(boolean militar) {
        this.militar = militar;
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
        return this.nome.equals(usu.nome);
    }
    
    public abstract char tipo();
    
}
