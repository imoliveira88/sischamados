package servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Chamado;
import javax.persistence.Query;

public class ChamadoServico extends DAOGenericoJPA<Long, Chamado>{

    public ChamadoServico() {
        super();
    }
    
    public void salvar(Chamado b) throws Exception {
            this.queryMataConexoes();
            if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
            super.getEm().persist(b);
            super.getEm().getTransaction().commit();
            super.getEm().close();
            this.queryMataConexoes();
    }
    
    //"chamado" já é um objeto recuperado
    public boolean atualizaChamado(Chamado chamado) throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();       
        
        try{
            super.getEm().merge(chamado);
            super.getEm().getTransaction().commit();
            super.getEm().close();
            this.queryMataConexoes();
            return true;
        }catch(Exception e){
            super.getEm().close();
            return false;
        }
    }
    
    // Retorna chamados entre duas datas distintas
    public List<Chamado> chamadosEntreDatas(Date dinicio, Date dfim) throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e FROM Chamado e WHERE e.data BETWEEN :data1 AND :data2 ORDER BY e.data DESC, e.status, e.solicitado");
        query.setParameter("data1",dinicio);
        query.setParameter("data2",dfim);
        
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            super.getEm().close();
            this.queryMataConexoes();
            return chamados;
        }
        catch(Exception e){
            super.getEm().close();
            return new ArrayList<>();
        }
     }
    
    // Retorna chamados de certo status entre duas datas distintas
    public List<Chamado> chamadosEntreDatasStatus(Date dinicio, Date dfim, String status) throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();

        Query query = super.getEm().createQuery("Select e FROM Chamado e WHERE (e.data BETWEEN :data1 AND :data2) AND e.status = :status ORDER BY e.data DESC");
        query.setParameter("data1",dinicio);
        query.setParameter("data2",dfim);
        query.setParameter("status",status);
        
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            super.getEm().close();
            this.queryMataConexoes();
            return chamados;
        }
        catch(Exception e){
            super.getEm().close();
            return new ArrayList<>();
        }
     }
    
    // Retorna chamados de certo status e divisão entre duas datas distintas
    public List<Chamado> chamadosEntreDatasStatusDivisao(Date dinicio, Date dfim, String status, String divisao) throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e FROM Chamado e WHERE (e.data BETWEEN :data1 AND :data2) AND e.status = :status AND e.solicitado = :divisao ORDER BY e.data DESC, e.status");
        query.setParameter("data1",dinicio);
        query.setParameter("data2",dfim);
        query.setParameter("status",status);
        query.setParameter("divisao",divisao);
        
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            super.getEm().close();
            this.queryMataConexoes();
            return chamados;
        }
        catch(Exception e){
            super.getEm().close();
            return new ArrayList<>();
        }
     }
    
    public List<Chamado> chamadosEntreDatasDivisao(Date dinicio, Date dfim, String divisao) throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e FROM Chamado e WHERE (e.data BETWEEN :data1 AND :data2) AND e.solicitado = :divisao ORDER BY e.data DESC, e.status");
        query.setParameter("data1",dinicio);
        query.setParameter("data2",dfim);
        query.setParameter("divisao",divisao);
        
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            super.getEm().close();
            this.queryMataConexoes();
            return chamados;
        }
        catch(Exception e){
            super.getEm().close();
            return new ArrayList<>();
        }
     }
    
    
    //Método lista todos os chamados que se encontram no status pedido
    public List<Chamado> chamadosStatus(String status)throws Exception{
        this.queryMataConexoes();
        Query query = super.getEm().createNamedQuery("Chamado.porStatus");
        query.setParameter("status", status);
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            super.getEm().close();
            this.queryMataConexoes();
            return chamados;
        }
        catch(Exception e){
            super.getEm().close();
            return new ArrayList<>();
        }
    }
    
    //Método conta todos os chamados que se encontram no status pedido
    public int chamadosStatusConta(String status)throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("SELECT COUNT(e) FROM Chamado e WHERE e.status = :status");
        
        query.setParameter("status", status);
        
        try{
            return query.executeUpdate();
        }
        catch(Exception e){
            return 0;
        }
    }
    
    //Método dá a média de tempo de resolução de todos os chamados finalizados da divisao
    public double chamadosMedia(String divisao)throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("SELECT AVG(e.tempo_solucao) FROM Chamado e WHERE e.solicitado = :divisao AND e.status = 'Satisfeito'");
        
        query.setParameter("divisao", divisao);
        
        try{
            return (double) query.getSingleResult();
        }
        catch(Exception e){
            return 0;
        }
    }
    
    public List<Chamado> todosChamadosData()throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        
        Query query = super.getEm().createQuery("SELECT e FROM Chamado e ORDER BY e.id DESC, e.data DESC, e.status, e.solicitado");
        
        List<Chamado> resultado = new ArrayList<>();
        
        try{
            resultado = query.getResultList();
            super.getEm().close();
            this.queryMataConexoes();
            return resultado;
        }
        catch(Exception e){
            super.getEm().close();
            return resultado;
        }
    }
    
    //Se daPara="da" retorna os chamados da Divisão, se "para" retorna os chamados PARA a divisao
    public List<Chamado> chamadosDivisao(String daPara, String parametro)throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        
        Query query;
        
        if(daPara.equals("da")) query = super.getEm().createQuery("SELECT e FROM Chamado e WHERE e.solicitante.divisao.nome = :parametro ORDER BY e.data DESC, e.id DESC, e.status, e.solicitado");
        else query = super.getEm().createQuery("SELECT e FROM Chamado e WHERE e.solicitado = :parametro ORDER BY e.data DESC, e.id DESC, e.status, e.solicitado");
        
        query.setParameter("parametro",parametro);
        List<Chamado> resultado = new ArrayList<>();
        
        try{
            resultado = query.getResultList();
            super.getEm().close();
            this.queryMataConexoes();
            return resultado;
        }
        catch(Exception e){
            super.getEm().close();
            return resultado;
        }
    }
    
    public List<Chamado> chamadosDivisaoStatus(String daPara, String parametro, String status)throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        Query query;
        
        if(daPara.equals("da")) query = super.getEm().createQuery("SELECT e FROM Chamado e WHERE e.status = :status AND e.solicitante.divisao.nome = :parametro ORDER BY e.data DESC, e.status, e.solicitado");
        else query = super.getEm().createQuery("SELECT e FROM Chamado e WHERE e.solicitado = :parametro AND e.status = :status ORDER BY e.data DESC, e.status, e.solicitante");
        
        List<Chamado> resultado = new ArrayList<>();
        query.setParameter("status",status);
        query.setParameter("parametro", parametro);
        
        try{
            resultado = query.getResultList();
            this.queryMataConexoes();
            super.getEm().close();
            return resultado;
        }
        catch(Exception e){
            super.getEm().close();
            return resultado;
        }
    }
    
}
