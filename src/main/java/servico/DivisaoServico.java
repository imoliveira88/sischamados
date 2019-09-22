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
    
    public boolean deletarDivisao(Long id) throws Exception{
        super.getEm().getTransaction().begin();
        
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
    
    public boolean salvar(Divisao b) throws Exception {
        if(!existeDivisao(b)){
            super.getEm().getTransaction().begin();
            super.getEm().persist(b);
            super.getEm().getTransaction().commit();
            super.getEm().close();
            return true;
        }
        return false;
    }
    
    public void atualizar(Divisao div) throws Exception{
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e.id FROM Divisao e WHERE e.numero = :num");
        query.setParameter("num",div.getNumero());
        
        Long id = (Long) query.getSingleResult();
        
        Divisao divisao = super.getEm().find(Divisao.class,id);
        divisao.setNome(div.getNome());
        divisao.setNumero(div.getNumero());
        super.getEm().merge(divisao);
        super.getEm().getTransaction().commit();
        super.getEm().close();
    }
    
    public Divisao retornaDivisao(int numero) throws Exception{
        TypedQuery<Divisao> query = super.getEm().createNamedQuery("Divisao.DIVISAO_POR_NUMERO", Divisao.class);
        
        query.setParameter("numero", numero);
        
       return query.getSingleResult();
    }
    
    public Divisao retornaDivisao(String nome) throws Exception{
        Query query = super.getEm().createQuery("SELECT e FROM Divisao e WHERE e.nome = :nome");
        query.setParameter("nome", nome);
        
       return (Divisao) query.getSingleResult();
    }
    
    //Houve diversos problemas com os resultados obtidos com esse método, no entanto com esta implementação obtivemos êxito
    public boolean existeDivisao(Divisao div) throws Exception{
        Query query = super.getEm().createQuery("SELECT COUNT(e) FROM Divisao e WHERE e.numero = :num");
        query.setParameter("num", div.getNumero());
       
        try{
            int quantidade = Integer.parseInt(query.getResultList().get(0).toString());
            super.getEm().close();
            if(quantidade > 0) return true;
        }
        catch(NoResultException e){
            return false;
        }
        return false;
    }
    
    public List<Divisao> todasDivisoes() throws Exception{
        TypedQuery<Divisao> query = super.getEm().createNamedQuery("Divisao.TODAS", Divisao.class);
        return query.getResultList();
    }

    public List<Divisao> divisoesPrestadoras() throws Exception {
        Query query = super.getEm().createQuery("SELECT e FROM Divisao e WHERE e.nome LIKE '%APOIO%' OR e.nome = 'PREFEITURA' OR e.nome LIKE '%ELETR%'");
        return query.getResultList();
    }
}
