/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import modelo.Pessoa;

public class PessoaServico extends DAOGenericoJPA<Long, Pessoa>{

    public PessoaServico() {
        super();
    }
    
    public Pessoa retornaPessoa(String nip){
        Query query = super.getEm().createNamedQuery("Pessoa.RetornaPessoa");
        
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
    
    //Retorna a id caso usuário exista e zero, caso não exista
    public boolean existePessoa(Pessoa usu){
        String query = "select e from Pessoa e";
        List<Pessoa> pessoas = super.getEm().createQuery(query, Pessoa.class).getResultList();
        try{
            for(Pessoa pessoa : pessoas){
                if(pessoa.equals(usu)){
                    return true;
                }
            }
            return false;
        }
        catch(NoResultException e){
            return false;
        }
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
    
    public Pessoa getById(long pk) {
        return super.getById(pk);
    }
    
}
