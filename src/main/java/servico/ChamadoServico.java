/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import java.util.ArrayList;
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
    
    public boolean atualizaStatus(Chamado c, Long id) throws Exception{
        super.getEm().getTransaction().begin();        
        
        Chamado cha = super.getEm().find(Chamado.class,id);
        //cha.setStatus(cha.getStatus() + "," + c.getStatus());
        cha.setStatus(c.getStatus());
        try{
            super.getEm().merge(cha);
            super.getEm().getTransaction().commit();
            super.getEm().close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public void atualizaSolicitado(Chamado c){
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e.id FROM Chamado e WHERE e.data = :data AND e.titulo = :titulo");
        query.setParameter("data",c.getData());
        query.setParameter("titulo",c.getTitulo());
        
        Long id = (Long) query.getSingleResult();
        
        Chamado cha = super.getEm().find(Chamado.class,id);
        cha.setStatus(cha.getSolicitado() + "," + c.getSolicitado());
        super.getEm().merge(cha);
        super.getEm().getTransaction().commit();
        super.getEm().close();
    }
    
    public void atualizaAtribuido(Chamado c){
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e.id FROM Chamado e WHERE e.data = :data AND e.titulo = :titulo");
        query.setParameter("data",c.getData());
        query.setParameter("titulo",c.getTitulo());
        
        Long id = (Long) query.getSingleResult();
        
        Chamado cha = super.getEm().find(Chamado.class,id);
        cha.setStatus(cha.getAtribuido() + "," + c.getAtribuido());
        super.getEm().merge(cha);
        super.getEm().getTransaction().commit();
        super.getEm().close();
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
        query.setParameter("soicitante", d.getNome());
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
