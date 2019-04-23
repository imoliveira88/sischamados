/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import modelo.Chamado;
import modelo.Divisao;
import modelo.Pessoa;

public class PessoaServico extends DAOGenericoJPA<Long, Pessoa>{

    public PessoaServico() {
        super();
    }
    
    public Pessoa retornaPessoa(String nip){
        Query query = super.getEm().createNamedQuery("Pessoa.retornaPessoa");
        
        query.setParameter("nip", nip);
        Pessoa usu;
        
        try{
            usu = (Pessoa) query.getSingleResult();
            return usu;
        }
        catch(NoResultException e){
            return null;
        }
    }
    
    
    public void atualizar(Pessoa p){
        super.getEm().getTransaction().begin();
        Query query = super.getEm().createQuery("Select e.id FROM Pessoa e WHERE e.nip = :nip");
        query.setParameter("nip",p.getNip());
        
        Long id = (Long) query.getSingleResult();
        
        Pessoa pes = super.getEm().find(Pessoa.class,id);
        pes.setNome(pes.getNome());
        pes.setNip(pes.getNip());
        pes.setChamados(pes.getChamados());
        pes.setEspecialidade(pes.getEspecialidade());
        pes.setMilitar(pes.getMilitar());
        pes.setPosto(pes.getPosto());
        pes.setSenha(pes.getSenha());
        super.getEm().merge(pes);
        super.getEm().getTransaction().commit();
        super.getEm().close();
    }
    
    public void excluir(Long id){
        super.getEm().getTransaction().begin();
        
        Pessoa pes = super.getEm().find(Pessoa.class,id);
        super.getEm().remove(pes);
        super.getEm().getTransaction().commit();
        super.getEm().close();
    }
    
    //Retorna a id caso usuário exista e zero, caso não exista
    public boolean existePessoa(Pessoa usu) throws NoResultException, IndexOutOfBoundsException{
        Query query = super.getEm().createQuery("SELECT count(e) FROM Pessoa e WHERE e.nip = :nip");
        query.setParameter("nip", usu.getNip());
       
        try{
            int quantidade = Integer.parseInt(query.getResultList().get(0).toString());
            System.out.println("Primeiro número " + quantidade);
            if(quantidade > 0) return true;
        }
        catch(NoResultException | IndexOutOfBoundsException e){
            return false;
        }
        return false;
    }
    
    public boolean salvar(Pessoa b) {
        if(!existePessoa(b)){
            super.getEm().getTransaction().begin();
            super.getEm().persist(b);
            super.getEm().getTransaction().commit();
            return true;
        }
        return false;
    }
    
    public List<Pessoa> pessoasDivisao(Divisao d)throws NoResultException{
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
    
    public List<Pessoa> pessoas()throws NoResultException{
        Query query = super.getEm().createNamedQuery("Pessoa.TODAS");
        List<Pessoa> pessoas;
        
        try{
            pessoas = query.getResultList();
            return pessoas;
        }
        catch(NoResultException e){
            return new ArrayList<>();
        }
    }

    
    public Pessoa getById(long pk) {
        return super.getById(pk);
    }
    
}
