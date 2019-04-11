/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import servico.DivisaoServico;
import servico.PessoaServico;

/**
Classe de teste
 */
public class main {
    
    public static void main(String[] args){
        String teste = "Igor,Magalhaes,Oliveira";
        Chamado c = new Chamado();
        
        Divisao d = new Divisao();
        d.setNome("Exocet");
        d.setNumero(232);
        
        DivisaoServico ds = new DivisaoServico();
        ds.salvar(d);
        
        Pessoa p = new Pessoa("Igor","oxente","18031480",true,"EN","1T");
        p.setDivisao(d);
        PessoaServico ps = new PessoaServico();
        ps.salvar(p);
        
        System.out.println("Último: " + c.ultimo(teste) + "\n" + "Tracking: " + c.exibeTrack(teste));
    }
    
}
