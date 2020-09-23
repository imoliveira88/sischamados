/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.SQLException;
import java.text.ParseException;
import servico.PessoaServico;

/**
Classe de teste
 */
public class main {
    
    public static void main(String[] args) throws SQLException, ParseException{
        /*PessoaServico ps = new PessoaServico();
        
        Pessoa p = new Pessoa("admin","admincmasm","admin",true,"","");
        p.setTipo('A');
        ps.salvar(p); */
        
        System.out.println("1254 " + (new Divisao()).primeiroDigito(1254));
        System.out.println("1 " + (new Divisao()).primeiroDigito(1));
        System.out.println("81 " + (new Divisao()).primeiroDigito(81));
        
        System.out.println("81 - semprim " + (new Divisao()).semPrimeiroDigito(81));

        }
    
}
