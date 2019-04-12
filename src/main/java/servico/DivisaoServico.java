/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.Divisao;

public class DivisaoServico extends DAOGenericoJPA<Long, Divisao>{

    public DivisaoServico() {
        super();
    }

    public Divisao getById(long pk) {
        return super.getById(pk);
    }
    
    public boolean deletarDivisao(Divisao d){
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e.id FROM Divisao e WHERE e.numero = :num");
        query.setParameter("num",d.getNumero());
        
        Long id = (Long) query.getSingleResult();
        
        Divisao d1 = super.getEm().find(Divisao.class,id);
        
        try{
            super.getEm().remove(d1);
            super.getEm().getTransaction().commit();
            super.getEm().close();
            return true;
        }catch(Exception e){
            super.getEm().close();
            return false;
        }
   
    }
    
    public boolean salvar(Divisao b) {
        if(!existeDivisao(b)){
            super.getEm().getTransaction().begin();
            super.getEm().persist(b);
            super.getEm().getTransaction().commit();
            return true;
        }
        return false;
    }
    
    public void atualizar(Divisao div){
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e.id FROM Divisao e WHERE e.numero = :num");
        query.setParameter("num",div.getNumero());
        
        Long id = (Long) query.getSingleResult();
        
        Divisao divisao = super.getEm().find(Divisao.class,id);
        divisao.setNome(div.getNome());
        divisao.setNumero(div.getNumero());
        super.getEm().merge(divisao);
        super.getEm().getTransaction().commit();
        super.getEm().close();;
    }
    
    public Divisao retornaDivisao(int numero){
        TypedQuery<Divisao> query = super.getEm().createNamedQuery("Divisao.DIVISAO_POR_NUMERO", Divisao.class);
        
        query.setParameter(1, numero);
        
       return query.getSingleResult();
    }
    
    //Houve diversos problemas com os resultados obtidos com esse método, no entanto com esta implementação obtivemos êxito
    public boolean existeDivisao(Divisao div){
        Query query = super.getEm().createQuery("SELECT COUNT(e) FROM Divisao e WHERE e.numero = :num");
        query.setParameter("num", div.getNumero());
       
        Long quantidade = (Long) query.getResultList().get(0);
       
        System.out.println("Primeiro número " + quantidade);
        try{
            if(quantidade > 0) return true;
        }
        catch(NoResultException e){
            return false;
        }
        return false;
    }
    
    public List<Divisao> todasDivisoes(){
        TypedQuery<Divisao> query = super.getEm().createNamedQuery("Divisao.TODAS", Divisao.class);
        return query.getResultList();
    }
}
