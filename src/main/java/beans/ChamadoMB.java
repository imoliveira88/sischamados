
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
    private String exibir; //Guardará o status que se deseja exibir

    public ChamadoMB() {
        chamado = new Chamado();
        chamadoSelecionado = new Chamado();
        solicitante = new Pessoa();
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

    public String getExibir() {
        return exibir;
    }

    public void setExibir(String exibir) {
        this.exibir = exibir;
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
    
    public String atualizaChamado(Long id, String status, String destino) throws Exception {
        ChamadoServico pra = new ChamadoServico();
        Chamado cha = pra.getById(id);
        cha.setStatus(status);
        cha.setDescricao(descricao);
        long diff = Calendar.getInstance().getTimeInMillis() - cha.getData().getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (status.equals("Finalizado")) cha.setTempo_solucao((int) diffHours);
        if (pra.atualizaStatus(id, status)) {
            if (status.equals("Finalizado")) {
                adicionaMensagem("Chamado de número " + id + " finalizado!", "destinoAviso");
            } else {
                adicionaMensagem("Chamado de número " + id + " atualizado!", "destinoAviso");
            }
        } else {
            adicionaMensagem("Status não pode ser alterado!", "destinoAviso");
        }
        return destino;
    }
    
    public List<Chamado> getChamadosPorStatus(String status){
        return new ChamadoServico().chamadosStatus(status);
    }
    
    public List<Chamado> getChamadosPorDivisao(String div){
        return new ChamadoServico().chamadosDivisao(div);
    }
    
    public List<Chamado> getChamadosParaDivisao(String div){
        return new ChamadoServico().chamadosParaDivisao(div);
    }
    
    public List<Chamado> getChamadosPorDivisaoStatus(String div, String st){
        if(st.equals("TODOS")) return this.getChamadosPorDivisao(div);
        return new ChamadoServico().chamadosDivisaoStatus(div,st);
    }
    
    public List<Chamado> getChamadosParaDivisaoStatus(String div, String st){
        if(st.equals("TODOS")) return new ChamadoServico().chamadosParaDivisao(div);
        return new ChamadoServico().chamadosParaDivisaoStatus(div,st);
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
    
    public String atualizarLista(){
        return "meusChamados";
    }
    
    public String salvar(Long idsolicitante){        
        chamado.setData(Calendar.getInstance().getTime());
        chamado.setStatus("Iniciado");
        chamado.setDescricao(descricao);
        chamado.setTitulo(titulo);
        chamado.setSolicitado(solicitado);
        chamado.setPrioridade(prioridade);
        chamado.setSolicitante((new PessoaServico()).getById(idsolicitante));
        //chamado.setSolicitante((new PessoaServico()).getById(1));//Alterar após implementação da tela de login
        ChamadoServico chamadoDAO = new ChamadoServico();
        chamadoDAO.salvar(chamado);
        return "meusChamados";
    }
    
}
