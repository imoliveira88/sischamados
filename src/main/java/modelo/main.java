/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Calendar;
import servico.ChamadoServico;
import servico.DivisaoServico;
import servico.PessoaServico;

/**
Classe de teste
 */
public class main {
    
    public static void main(String[] args){
        String teste = "Igor,Magalhaes,Oliveira";
        Chamado c = new Chamado();
       
        
        Pessoa p = new Pessoa("Igor","oxente","15480",true,"EN","1T");
        //p.setDivisao((new DivisaoServico()).retornaDivisao(232));
        PessoaServico ps = new PessoaServico();
        //ps.salvar(p);
        
        
        c.setAtribuido("Igor");
        c.setData(Calendar.getInstance().getTime());
        c.setDescricao("Bobagem grande");
        c.setPrioridade("Alta");
        c.setSolicitado("16");
        c.setSolicitante(ps.retornaPessoa(p.getNip()));
        c.setStatus("Aberto");
        c.setTitulo("BOmba em Hiroshima!");
        
        ChamadoServico cs = new ChamadoServico();
        cs.salvar(c);
        
        
        
        System.out.println("Último: " + c.ultimo(teste) + "\n" + "Tracking: " + c.exibeTrack(teste));
    }
    
}
