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
        PessoaServico ps = new PessoaServico();
        
        Pessoa p = new Pessoa("admin","admin","admin",true,"","");
        p.setTipo('A');
        ps.salvar(p); 
        
        }
    
}
