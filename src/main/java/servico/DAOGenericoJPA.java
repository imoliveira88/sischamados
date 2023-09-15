package servico;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@SuppressWarnings("unchecked")
public class DAOGenericoJPA<PK, T> {
    private static final String PERSISTENCE_UNIT_NAME = "sischamados";
    private static EntityManagerFactory factory; 
    private static EntityManager em;
 
    public DAOGenericoJPA() {
        geraFactory();
    }
    
    public void queryMataConexoes() throws Exception{
        String query = "WITH inactive_connections AS (SELECT pid, rank() over (partition by client_addr order by backend_start ASC) as rank FROM pg_stat_activity WHERE pid <> pg_backend_pid( ) AND application_name !~ '(?:psql)|(?:pgAdmin.+)' AND datname = current_database() AND usename = current_user AND state in ('idle', 'idle in transaction', 'idle in transaction (aborted)', 'disabled') AND current_timestamp - state_change > interval '30 seconds' ) SELECT pg_terminate_backend(pid) FROM inactive_connections WHERE rank > 1";
        Query q = this.getEm().createNativeQuery(query);
        q.getResultList();
    }
    
    public void geraFactory(){
        if(factory == null || !factory.isOpen()) factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if(em == null || !em.isOpen()) em = factory.createEntityManager();
    }
    
    public EntityManagerFactory getFactory(){
        return factory;
    }
    
    public EntityManager getEm() throws Exception{
        //Talvez seja o caso de inserir a consulta e query de finalização aqui
        if(em == null || !em.isOpen()){
            //this.encerraConexoes();
            this.geraFactory();
        }
        return em;
    }
    
    public static void fecharFabrica(){
        if(em.isOpen()) em.close();
        if(factory.isOpen()) factory.close();
    }
    
    public void excluir(PK pk) throws Exception{
        this.queryMataConexoes();
        if(!em.getTransaction().isActive()) em.getTransaction().begin();
        
        T elemento = (T) em.find(getTypeClass(), pk);
        em.remove(elemento);
        em.getTransaction().commit();
        em.close();
    }
 
    public T getById(PK pk) {
        T elemento = (T) em.find(getTypeClass(), pk);
        em.close();
        return elemento;
    }
 
    public List<T> findAll() {
        List<T> elementos = em.createQuery(("FROM " + getTypeClass().getName())).getResultList();
        em.close();
        return elementos;
    }
 
    private Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        return clazz;
    }
    
}