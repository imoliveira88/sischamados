/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Chamado;
import servico.ChamadoServico;

/**
 *
 * @author usuario
 */
@ManagedBean(name = "chamadoMB")
@SessionScoped
public class ChamadoMB extends Artificial{
    
    private Chamado chamado;

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }
    
    /*public String adicionaItem(){
        ItemPedido ip = new ItemPedido(this.prato,this.quantidade,pedido);
        
        pedido.addItem(ip);
        this.addItem(ip);
        
        
        this.setMensagem("Item adicionado ao pedido!");
        return "homeC";
    }*/
    
    public String fechaChamado(){
        ChamadoServico chamadoDAO = new ChamadoServico();
        this.chamado.setTempo_solucao(2);    //(Math.floor((Calendar.getInstance().getTimeInMillis()-chamado.getData())/(1000*3600*24)));
        chamadoDAO.save(chamado);
        
        this.adicionaMensagem("Chamado concluído!","destinoAviso");
        return "chamados";
    }
    
    public List<Chamado> getChamadosPorStatus(String status){
        return new ChamadoServico().chamadosStatus(status);
    }
    
    
}
