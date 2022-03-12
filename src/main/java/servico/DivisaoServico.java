package servico;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import modelo.Divisao;

public class DivisaoServico extends DAOGenericoJPA<Long, Divisao>{

    public DivisaoServico() {
        super();
    }
    
    public boolean salvar(Divisao b) throws Exception {
        this.queryMataConexoes();
        if(!existeDivisao(b)){
            if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
            super.getEm().persist(b);
            super.getEm().getTransaction().commit();
            super.getEm().close();
            return true;
        }
        return false;
    }
    
    public void atualizar(Divisao div) throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
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
    
    public Divisao retornaDivisao(String tipo, String parametro) throws Exception{
        Query query;
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        
        if(tipo.equals("nome")) query = super.getEm().createQuery("SELECT e FROM Divisao e WHERE e.nome = :parametro");
        else query = super.getEm().createQuery("SELECT e FROM Divisao e WHERE e.numero = :parametro");
            
        query.setParameter("parametro", parametro);
        
        Divisao div;
        
        try{
            div = (Divisao) query.getSingleResult();
            super.getEm().close();
            return div;
        }
        catch(NoResultException e){
            super.getEm().close();
            return null;
        }
        
    }
    
    //Houve diversos problemas com os resultados obtidos com esse método, no entanto com esta implementação obtivemos êxito
    public boolean existeDivisao(Divisao div) throws Exception{
        this.queryMataConexoes();
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

    public List<Divisao> divisoesPrestadoras() throws Exception {
        Query query = super.getEm().createQuery("SELECT e FROM Divisao e WHERE e.prestadora=TRUE");
        return query.getResultList();
    }
    
    public List<Divisao> divisoesOrdenadasNome() throws Exception {
        Query query = super.getEm().createQuery("SELECT e FROM Divisao e ORDER BY e.nome");
        return query.getResultList();
    }
}
