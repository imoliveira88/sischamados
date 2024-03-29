package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_DIVISAO")
public class Divisao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DIVISAO")
    private Long id;
    
    public Divisao(){
        this.pessoas = new ArrayList<>();
    }
    
    @Size(min = 2, max = 40)
    @NotNull
    @Column(name = "NOME")
    private String nome;
    
    @NotNull
    @Column(name = "NUMERO")
    private int numero;
    
    @NotNull
    @Column(name = "PRESTADORA")
    private boolean prestadora;
    
    @OneToMany(mappedBy = "divisao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Pessoa> pessoas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isPrestadora() {
        return prestadora;
    }

    public void setPrestadora(boolean prestadora) {
        this.prestadora = prestadora;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }
    
    public int primeiroDigito(int numero){
        return numero/((int) Math.pow(10, (int) Math.log10(numero)));
    }
    
    public int semPrimeiroDigito(int numero){
        return numero - (int) Math.pow(10, (int) Math.log10(numero))*this.primeiroDigito(numero);
    }
    
    public String numeroString(){
        if(primeiroDigito(this.numero) == 9) return "0" + this.semPrimeiroDigito(this.numero);
        if(this.numero < 10) return "0" + this.numero;
        return "" + this.numero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Divisao)) {
            return false;
        }
        Divisao other = (Divisao) object;
        return !(this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
}
