/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import modelo.Chamado;
import modelo.Pessoa;
import servico.ChamadoServico;
import servico.PessoaServico;

/**
 *
 * @author usuario
 */
@Named("chamadoMB")
@SessionScoped
public class ChamadoMB extends Artificial implements Serializable{
    
    private Chamado chamado;
    private String solicitado;
    private String titulo;
    private String status;
    private String descricao;
    private String prioridade;
    private Pessoa solicitante;
    private Chamado chamadoSelecionado;

    public ChamadoMB() {
        chamado = new Chamado();
        solicitante = new Pessoa();
        chamadoSelecionado = new Chamado();
    }

    public Chamado getChamado() {
        return chamado;
    }
    
    public List listaStatus(){
        List lista = new ArrayList<>();
        lista.add("Iniciado");
        lista.add("Executando");
        lista.add("Executado");
        lista.add("Finalizado");
        return lista;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

    public Chamado getChamadoSelecionado() {
        return chamadoSelecionado;
    }

    public void setChamadoSelecionado(Chamado chamadoSelecionado) {
        this.chamadoSelecionado = chamadoSelecionado;
    }

    public String getSolicitado() {
        return solicitado;
    }

    public Pessoa getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Pessoa solicitante) {
        this.solicitante = solicitante;
    }
    
    

    public void setSolicitado(String solicitado) {
        this.solicitado = solicitado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    } 

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String fechaChamado(){
        ChamadoServico chamadoDAO = new ChamadoServico();
        this.chamado.setTempo_solucao(2);    //(Math.floor((Calendar.getInstance().getTimeInMillis()-chamado.getData())/(1000*3600*24)));
        chamadoDAO.save(chamado);
        
        this.adicionaMensagem("Chamado concluído!","destinoAviso");
        return "chamados";
    }
    
    public String atualizaStatus(Long id, String status) throws Exception{
        ChamadoServico pra = new ChamadoServico();
        Chamado cha = pra.getById(id);
        cha.setStatus(status);
        if(pra.atualizaStatus(id,status)){
            System.out.println("AQUIIIIIII" + "Status = " + status + "  Id =    " + id + cha.getTitulo() + cha.getData());
            adicionaMensagem("Status alterado com sucesso!","destinoAviso");
        }else{
            adicionaMensagem("Status não pode ser alterado!","destinoAviso");
        }
        return "chamados";
    }
    
    public List<Chamado> getChamadosPorStatus(String status){
        return new ChamadoServico().chamadosStatus(status);
    }
    
    public List<Chamado> getChamadosPorDivisao(String div){
        return new ChamadoServico().chamadosDivisao(div);
    }
    
    public List<Chamado> getChamadosEntreDatas(Date dinicio, Date dfim){
        return new ChamadoServico().chamadosEntreDatas(dinicio, dfim);
    }

    public List<Chamado> getChamadosEntreDatasStatusDivisao(Date dinicio, Date dfim, String status, String divisao){
        return new ChamadoServico().chamadosEntreDatasStatusDivisao(dinicio, dfim, status, divisao);
    }
    
    public List<Chamado> getChamadosEntreDatasStatus(Date dinicio, Date dfim, String status){
        return new ChamadoServico().chamadosEntreDatasStatus(dinicio, dfim, status);
    }
    
    public List<Chamado> getChamados(){
        return new ChamadoServico().todosChamados();
    }
    
    public String salvar(){        
        chamado.setData(Calendar.getInstance().getTime());
        chamado.setStatus("Iniciado");
        chamado.setDescricao(descricao);
        chamado.setTitulo(titulo);
        chamado.setSolicitado(solicitado);
        chamado.setPrioridade(prioridade);
        chamado.setSolicitante((new PessoaServico()).retornaPessoa(this.solicitante.getNip()));
        //chamado.setSolicitante((new PessoaServico()).getById(1));//Alterar após implementação da tela de login
        ChamadoServico chamadoDAO = new ChamadoServico();
        chamadoDAO.salvar(chamado);
        return "chamados";
    }
    
}
