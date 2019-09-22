/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
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
    
    public void insertWithQuery() throws Exception {
        if(inserirSQL(this.sql) == 1){
            this.sql = "";
            this.adicionaMensagem("Registro inserido!", "destinoAviso", "SUCESSO!");
        } else this.adicionaMensagem("Registro não inserido!", "destinoAviso", "ERRO!");
    }
    
    @Transactional
    public int inserirSQL(String query) throws Exception {
        try {
            DAOGenericoJPA djpa = new DAOGenericoJPA();
            djpa.getEm().getTransaction().begin();
            djpa.getEm().createNativeQuery(query).executeUpdate();
            djpa.getEm().getTransaction().commit();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }    
    
    
    public List<String> separaQueries(String queries){
        List<String> lista = new ArrayList<>();
        String temp = "";
        for(int i=0; i<queries.length(); i++){
            if(queries.charAt(i) == ';'){
                lista.add(temp);
                temp="";
            }else temp += queries.charAt(i);
        }
        return lista;
    }
    
    public void variosInserts(String queries) throws Exception{
        List<String> lista = separaQueries(queries);
        int numQueries = lista.size();
        int executadas = 0;
        try{
            for(int i=0;i<numQueries;i++){
                executadas += inserirSQL(lista.get(i));
            }
            this.adicionaMensagem("Executadas " + numQueries + " queries, inseridos " + executadas + " registros!", "destinoAviso", "SUCESSO!");
        }catch(Exception e){
            this.adicionaMensagem("Registros não inseridos!", "destinoAviso", "ERRO!");
        }
    }
    
    public void adicionaMensagem(String mensagem, String destino, String parecer){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        msg = new FacesMessage(parecer,mensagem);
        context.addMessage(destino, msg);
        
    }
}
