/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Chamado;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import modelo.Divisao;
import modelo.Pessoa;

public class ChamadoServico extends DAOGenericoJPA<Long, Chamado>{

    public ChamadoServico() {
        super();
    }
    
    public Chamado getById(long pk) {
        return super.getById(pk);
    }
    
    public void salvar(Chamado b) {
            super.getEm().getTransaction().begin();
            super.getEm().persist(b);
            super.getEm().getTransaction().commit();
    }
    
      //Solicitado, status, atribuído a: os três são Strings formatadas
    
    //"chamado" já é um objeto recuperado
    public boolean atualizaChamado(Chamado chamado) throws Exception{
        super.getEm().getTransaction().begin();        
        
        try{
            super.getEm().merge(chamado);
            super.getEm().getTransaction().commit();
            super.getEm().close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    // Retorna chamados entre duas datas distintas
    public List<Chamado> chamadosEntreDatas(Date dinicio, Date dfim){
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e FROM Chamado e WHERE e.data BETWEEN :data1 AND :data2 ORDER BY e.data DESC, e.status, e.solicitado");
        query.setParameter("data1",dinicio);
        query.setParameter("data2",dfim);
        
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            return chamados;
        }
        catch(NoResultException e){
            return new ArrayList<>();
        }
     }
    
    // Retorna chamados de certo status entre duas datas distintas
    public List<Chamado> chamadosEntreDatasStatus(Date dinicio, Date dfim, String status){
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e FROM Chamado e WHERE (e.data BETWEEN :data1 AND :data2) AND e.status = :status ORDER BY e.data DESC, e.status, e.solicitado");
        query.setParameter("data1",dinicio);
        query.setParameter("data2",dfim);
        query.setParameter("status",status);
        
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            return chamados;
        }
        catch(NoResultException e){
            return new ArrayList<>();
        }
     }
    
    // Retorna chamados de certo status e divisão entre duas datas distintas
    public List<Chamado> chamadosEntreDatasStatusDivisao(Date dinicio, Date dfim, String status, String divisao){
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e FROM Chamado e WHERE (e.data BETWEEN :data1 AND :data2) AND e.status = :status AND e.solicitado = :divisao ORDER BY e.data DESC, e.status");
        query.setParameter("data1",dinicio);
        query.setParameter("data2",dfim);
        query.setParameter("status",status);
        query.setParameter("divisao",divisao);
        
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            return chamados;
        }
        catch(NoResultException e){
            return new ArrayList<>();
        }
     }
    
    public List<Chamado> chamadosEntreDatasDivisao(Date dinicio, Date dfim, String divisao){
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e FROM Chamado e WHERE (e.data BETWEEN :data1 AND :data2) AND e.solicitado = :divisao ORDER BY e.data DESC, e.status");
        query.setParameter("data1",dinicio);
        query.setParameter("data2",dfim);
        query.setParameter("divisao",divisao);
        
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            return chamados;
        }
        catch(NoResultException e){
            return new ArrayList<>();
        }
     }
    
    
    //Método lista todos os chamados que se encontram no status pedido
    public List<Chamado> chamadosStatus(String status)throws NoResultException{
        Query query = super.getEm().createNamedQuery("Chamado.porStatus");
        query.setParameter("status", status);
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            return chamados;
        }
        catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
    //Método conta todos os chamados que se encontram no status pedido
    public int chamadosStatusConta(String status)throws NoResultException{
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("SELECT COUNT(e) FROM Chamado e WHERE e.status = :status");
        
        query.setParameter("status", status);
        
        try{
            return query.executeUpdate();
        }
        catch(NoResultException e){
            return 0;
        }
    }
    
    //Método dá a média de tempo de resolução de todos os chamados finalizados da divisao
    public double chamadosMedia(String divisao)throws NoResultException, NullPointerException{
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("SELECT AVG(e.tempo_solucao) FROM Chamado e WHERE e.solicitado = :divisao AND e.status = 'Satisfeito'");
        
        query.setParameter("divisao", divisao);
        
        try{
            return (double) query.getSingleResult();
        }
        catch(NoResultException | NullPointerException e){
            return 0;
        }
    }
    
    public List<Chamado> chamadosDivisao(String divisao)throws NoResultException, NullPointerException{
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("SELECT e FROM Chamado e ORDER BY e.data DESC, e.status, e.solicitado");
        List<Chamado> chamados;
        List<Chamado> resultado = new ArrayList<>();
        
        try{
            chamados = query.getResultList();
            for (int i = 0; i < chamados.size(); i++) {
                if (chamados.get(i).getSolicitante() != null) {
                    if (chamados.get(i).getSolicitante().getDivisao().getNome().equals(divisao)) {
                        resultado.add(chamados.get(i));
                    }
                }
            }
            return resultado;
        }
        catch(NoResultException | NullPointerException e){
            return resultado;
        }
    }
    
    //Lista todos os chamados que têm "divisao" como solicitada
    public List<Chamado> chamadosParaDivisao(String divisao)throws NoResultException, NullPointerException{
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("SELECT e FROM Chamado e WHERE e.solicitado = :divisao ORDER BY e.data DESC, e.status, e.solicitado");
        query.setParameter("divisao",divisao);

        List<Chamado> resultado = new ArrayList<>();
        
        
        
        try{
            resultado = query.getResultList();
            return resultado;
        }
        catch(NoResultException | NullPointerException e){
            return resultado;
        }
    }
    
    public List<Chamado> chamadosDivisaoStatus(String divisao, String status)throws NoResultException, NullPointerException{
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("SELECT e FROM Chamado e WHERE e.status = :status ORDER BY e.data DESC, e.status, e.solicitado");
        List<Chamado> chamados;
        List<Chamado> resultado = new ArrayList<>();
        query.setParameter("status",status);
        
        try{
            chamados = query.getResultList();
            for (int i = 0; i < chamados.size(); i++) {
                if (chamados.get(i).getSolicitante() != null) {
                    if (chamados.get(i).getSolicitante().getDivisao().getNome().equals(divisao)) {
                        resultado.add(chamados.get(i));
                    }
                }
            }
            return resultado;
        }
        catch(NoResultException | NullPointerException e){
            return resultado;
        }
    }
    
    public List<Chamado> chamadosParaDivisaoStatus(String divisao, String status)throws NoResultException, NullPointerException{
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("SELECT e FROM Chamado e WHERE e.solicitado = :solicitado AND e.status = :status ORDER BY e.data DESC, e.status, e.solicitado");
        List<Chamado> resultado = new ArrayList<>();
        
        query.setParameter("solicitado",divisao);
        query.setParameter("status",status);
        
        try{
            resultado = query.getResultList();
            return resultado;
        }
        catch(NoResultException | NullPointerException e){
            return resultado;
        }
    }
    
    public List<Chamado> todosChamados()throws NoResultException{
        Query query = super.getEm().createNamedQuery("Chamado.TODOS");
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            return chamados;
        }
        catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
    public List<Chamado> chamadosDivisao(Divisao d)throws NoResultException{
        Query query = super.getEm().createNamedQuery("Chamado.porDivisao");
        query.setParameter("solicitante", d.getNome());
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            return chamados;
        }
        catch(NoResultException e){
            return new ArrayList<>();
        }
    }


    public List<Chamado> chamadosSolicitante(Pessoa sol)throws NoResultException{
        Query query = super.getEm().createNamedQuery("Chamado.porSolicitante");
        query.setParameter("solicitante", sol);
        List<Chamado> chamados;
        
        try{
            chamados = query.getResultList();
            return chamados;
        }
        catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
}
