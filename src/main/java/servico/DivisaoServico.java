/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import java.util.List;
import javax.persistence.NoResultException;
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
        d = super.getEm().merge(d);
        super.getEm().remove(d);
        super.getEm().getTransaction().commit();
        return true; //AVERIGUAR FUNCIONAMENTO
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
    
    public Divisao retornaDivisao(int numero){
        TypedQuery<Divisao> query = super.getEm().createNamedQuery("Divisao.DIVISAO_POR_NUMERO", Divisao.class);
        
        query.setParameter(1, numero);
        
       return query.getSingleResult();
    }
    
    public boolean existeDivisao(Divisao div){
        String query = "select e from Divisao e";
        List<Divisao> divisoes = super.getEm().createQuery(query, Divisao.class).getResultList();
        try{
            for(Divisao divisao: divisoes){
                if(divisao.equals(div)){
                    return true;
                }
            }
            return false;
        }
        catch(NoResultException e){
            return false;
        }
    }
    
    public List<Divisao> todasDivisoes(){
        TypedQuery<Divisao> query = super.getEm().createNamedQuery("Divisao.TODAS", Divisao.class);
        return query.getResultList();
    }
}
