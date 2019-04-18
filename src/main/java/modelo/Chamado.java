/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "TB_CHAMADO")
@NamedQueries(value = 
        {@NamedQuery(name = "Chamado.porStatus", query= " SELECT u FROM Chamado u WHERE u.status = :status  ORDER BY u.data DESC"),
         @NamedQuery(name = "Chamado.TODOS", query= " SELECT u FROM Chamado u ORDER BY u.data DESC"),
         @NamedQuery(name = "Chamado.porDivisao", query= " SELECT u FROM Chamado u WHERE u.solicitado = :solicitado ORDER BY u.data DESC"),
         @NamedQuery(name = "Chamado.porSolicitante", query= " SELECT u FROM Chamado u WHERE u.solicitante = :solicitante ORDER BY u.data DESC")})
public class Chamado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CHAMADO")
    private Long id;
    
    //@NotNull
    @ManyToOne
    @JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID_PESSOA")
    private Pessoa solicitante;
    
    @Size(min = 2, max = 40)
    @NotNull
    @Column(name = "TITULO")
    private String titulo;
    
    @NotNull
    @Column(name = "PRIORIDADE")
    private String prioridade; //Valores pré-definidos
    
    @Size(min = 2, max = 40)
    @NotNull
    @Column(name = "DESCRICAO")
    private String descricao;
    
    @Column(name = "DATA_ABERTURA")
    private Date data;
    
    @Column(name = "SOLICITADO")
    private String solicitado; //Track do setor solicitado
    
    @Column(name = "STATUS")
    private String status; //Track do status do chamado
    
    @Column(name = "ATRIBUIDO_A")
    private String atribuido; //Track das atribuições
    
    @Column(name = "TEMPO_SOLUCAO")
    private int tempo_solucao; //Tempo dado em dias. Quando o status for definido como "Executado", atualizar também esse campo
    
    public String ultimo(String s){
        String aux = "";
        for(int i=0; i < s.length(); i++){
            if(s.charAt(i)==',' ) aux = "";
            else aux += s.charAt(i);
        }
        return aux;
    }
    
    public String exibeTrack(String s){
        String aux = "";
        for(int i=0; i < s.length(); i++){
            if(s.charAt(i)==',' ) aux += " => ";
            else aux += s.charAt(i);
        }
        return aux;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Pessoa getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Pessoa solicitante) {
        this.solicitante = solicitante;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(String solicitado) {
        this.solicitado = solicitado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAtribuido() {
        return atribuido;
    }

    public void setAtribuido(String atribuido) {
        this.atribuido = atribuido;
    }

    public int getTempo_solucao() {
        return tempo_solucao;
    }

    public void setTempo_solucao(int tempo_solucao) {
        this.tempo_solucao = tempo_solucao;
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
        if (!(object instanceof Chamado)) {
            return false;
        }
        Chamado other = (Chamado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Chamado[ id=" + id + " ]";
    }
    
}
