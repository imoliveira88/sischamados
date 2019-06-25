/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.Transactional;
import servico.DAOGenericoJPA;

@ManagedBean(name = "artificialMB")
@SessionScoped
public class Artificial {
    private String sql;

    public Artificial() {
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
    
    @Transactional
    public void insertWithQuery() throws Exception {
        try {
            DAOGenericoJPA djpa = new DAOGenericoJPA();
            djpa.getEm().getTransaction().begin();
            djpa.getEm().createNativeQuery(this.sql).executeUpdate();
            djpa.getEm().getTransaction().commit();
            this.sql = "";
            this.adicionaMensagem("Registro inserido!", "destinoAviso", "SUCESSO!");
        } catch (Exception e) {
            this.adicionaMensagem("Registro não inserido!", "destinoAviso", "ERRO!");
        }
    }
    
    public void adicionaMensagem(String mensagem, String destino, String parecer){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        msg = new FacesMessage(parecer,mensagem);
        context.addMessage(destino, msg);
        
    }
}
