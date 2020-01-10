package servico;

import beans.PessoaMB;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import modelo.Divisao;
import modelo.Pessoa;

public class PessoaServico extends DAOGenericoJPA<Long, Pessoa>{

    public PessoaServico() {
        super();
    }
    
    public long numeroPessoasDivisao(Divisao div) throws Exception{
        this.queryMataConexoes();
        Query query = super.getEm().createNamedQuery("Pessoa.retornaQtdPessoasDivisao");
        
        query.setParameter("divisao", div);
        
        try{
            return (long) query.getSingleResult();
        }
        catch(NoResultException e){
            return 0;
        }
    }
    
    public int excluiTransfere(Long id) throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        
        Pessoa pes = super.getEm().find(Pessoa.class,id);
        
        /*
         - Achar os chamados criados por aquela pessoa
         - Pegar a divisão daquela pessoa
         - Escolher outro militar daquela divisão
         - Atualizar todos aqueles chamados
        */
        
        super.getEm().merge(pes);
        super.getEm().getTransaction().commit();
        super.getEm().close();
        return 0;
    }
    
    public Pessoa retornaPessoa(String tipo, String parametro) throws Exception{//Tipo pode ser nome ou nip
        Query query;
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        if(tipo.equals("nome")) query = super.getEm().createQuery("Select e FROM Pessoa e WHERE e.nome = :parametro");
        else query = super.getEm().createQuery("Select e FROM Pessoa e WHERE e.nip = :parametro");
        
        query.setParameter("parametro", parametro);
        
        Pessoa usu;
        
        
        try{
            usu = (Pessoa) query.getSingleResult();
            super.getEm().close();
            return usu;
        }
        catch(NoResultException e){
            super.getEm().close();
            return null;
        }
    }
    
    
    public boolean atualizar(Pessoa p) throws Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();       
        
        try{
            super.getEm().merge(p);
            super.getEm().getTransaction().commit();
            super.getEm().close();
            this.queryMataConexoes();
            return true;
        }catch(Exception e){
            super.getEm().close();
            return false;
        }
    }
    
    public void resetaSenha(Long id) throws NoSuchAlgorithmException, Exception{
        this.queryMataConexoes();
        if(!super.getEm().getTransaction().isActive()) super.getEm().getTransaction().begin();
        
        Pessoa pes = super.getEm().find(Pessoa.class,id);
        pes.setSenha((new PessoaMB()).hash("aB123456@"));
        super.getEm().merge(pes);
        super.getEm().getTransaction().commit();
        super.getEm().close();
    }
    
    public boolean existePessoa(Pessoa usu) throws NoResultException, IndexOutOfBoundsException, Exception{
        Query query = super.getEm().createQuery("SELECT count(e) FROM Pessoa e WHERE e.nip = :nip");
        query.setParameter("nip", usu.getNip());
        
        this.queryMataConexoes();
       
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
    
    public boolean salvar(Pessoa b) throws SQLException, ParseException {
        try {
            this.queryMataConexoes();
            if (!existePessoa(b)) {
                if (!super.getEm().getTransaction().isActive()) {
                    super.getEm().getTransaction().begin();
                }
                super.getEm().persist(b);
                super.getEm().getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Pessoa> pessoasDivisao(Divisao d)throws NoResultException, Exception{
        this.queryMataConexoes();
        Query query = super.getEm().createNamedQuery("Pessoa.retornaPessoasDivisao");
        query.setParameter("divisao", d);
        List<Pessoa> pessoas;
        
        try{
            pessoas = query.getResultList();
            return pessoas;
        }
        catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
}
