/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import beans.ChamadoMB;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import servico.ChamadoServico;
import servico.PessoaServico;

/**
Classe de teste
 */
public class main {
    
    public static void main(String[] args) throws SQLException, ParseException{
        /*String teste = "Igor,Magalhaes,Oliveira";
        Chamado c = new Chamado();
       
        
        Pessoa p = new Pessoa("admin","admin","admin",true,"","");
        p.setTipo('A');
        //p.setDivisao((new DivisaoServico()).retornaDivisao(232));
        PessoaServico ps = new PessoaServico();
        ps.salvar(p);*/
        
        System.out.println((new ChamadoMB()).listaProxStatus("Iniciado").toString());
        
      
        
        
        }
    
}
