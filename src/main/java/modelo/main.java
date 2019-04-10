/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
Classe de teste
 */
public class main {
    
    public static void main(String[] args){
        String teste = "Igor,Magalhaes,Oliveira";
        Chamado c = new Chamado();
        
        System.out.println("Último: " + c.ultimo(teste) + "\n" + "Tracking: " + c.exibeTrack(teste));
    }
    
}
