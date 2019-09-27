package servico;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SuppressWarnings("unchecked")
public class DAOGenericoJPA<PK, T> {
    private static final String PERSISTENCE_UNIT_NAME = "sischamados";
    private static EntityManagerFactory factory; 
    private static EntityManager em;
 
    public DAOGenericoJPA() {
        this.geraFactory();
    }
    
    public void geraFactory(){
        if(factory == null || !factory.isOpen()) factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if(em == null || !em.isOpen()) this.em = factory.createEntityManager();
    }
    
    public EntityManagerFactory getFactory(){
        return this.factory;
    }
    
    public EntityManager getEm() throws Exception{
        //Talvez seja o caso de inserir a consulta e query de finalização aqui
        if(em == null || !em.isOpen()){
            //this.encerraConexoes();
            if(factory == null || !factory.isOpen()) factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            this.em = factory.createEntityManager();
        }
        return this.em;
    }
    
    public static void fecharFabrica(){
        em.close();
        factory.close();
    }
 
    public T getById(PK pk) {
        return (T) em.find(getTypeClass(), pk);
    }
 
    public List<T> findAll() {
        return em.createQuery(("FROM " + getTypeClass().getName())).getResultList();
    }
    
    public int encerraConexoes() throws Exception {
        String query = "SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE (pid <> pg_backend_pid()) AND (SELECT sum(numbackends) FROM pg_stat_database) > 10";
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
 
    private Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        return clazz;
    }
    
}