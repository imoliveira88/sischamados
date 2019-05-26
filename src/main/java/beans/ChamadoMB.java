
package beans;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import modelo.Chamado;
import modelo.Divisao;
import modelo.Pessoa;
import servico.ChamadoServico;
import servico.PessoaServico;


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
    private Date dataInicial;
    private Date dataFinal;
    private String divisao;
    private List<Chamado> chamFiltrados;
    private List<Chamado> chamFiltradosDivisao;
    private int linhasExistentes;
    private String atribuido;
    private String texto;

    public ChamadoMB() {
        chamado = new Chamado();
        chamadoSelecionado = new Chamado();
        solicitante = new Pessoa();
        chamFiltrados = this.getChamados();
        chamFiltradosDivisao = new ArrayList<>();
        dataInicial = null;
        dataFinal = null;
    }

    public Chamado getChamado() {
        return chamado;
        
    }
    
    public List listaStatus(){
        List lista = new ArrayList<>();
        lista.add("Iniciado");
        lista.add("Delineando");
        lista.add("Aguardando material");
        lista.add("Aguardando execução");
        lista.add("Executando");
        lista.add("Executado");
        lista.add("Satisfeito");
        return lista;
    }
    
    public List listaProxStatus(String statusAtual){
        List lista = new ArrayList<>();
        lista.add("Iniciado");
        lista.add("Delineando");
        lista.add("Aguardando material");
        lista.add("Aguardando execução");
        lista.add("Executando");
        lista.add("Executado");
        lista.add("Satisfeito");
        
        int i = 0;
        
        do{
            if(!lista.get(i).equals(statusAtual)) lista.remove(lista.get(i));
            else return lista;
        }while(i<lista.size());
        return lista;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

    public List<Chamado> getChamFiltradosDivisao() {
        return chamFiltradosDivisao;
    }

    public void setChamFiltradosDivisao(List<Chamado> chamFiltradosDivisao) {
        this.chamFiltradosDivisao = chamFiltradosDivisao;
    }

    public String getAtribuido() {
        return atribuido;
    }

    public void setAtribuido(String atribuido) {
        this.atribuido = atribuido;
    }
    
    

    public List<Chamado> getChamFiltrados() {
        return chamFiltrados;
    }

    public void setChamFiltrados(List<Chamado> chamFiltrados) {
        this.chamFiltrados = chamFiltrados;
    }

    public Chamado getChamadoSelecionado() {
        return chamadoSelecionado;
    }

    public void setChamadoSelecionado(Chamado chamadoSelecionado) {
        this.chamadoSelecionado = chamadoSelecionado;
    }

    public String getDivisao() {
        return divisao;
    }

    public void setDivisao(String divisao) {
        this.divisao = divisao;
    }  

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
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
    
    public String exibeChamado(Long id){
        chamadoSelecionado = (new ChamadoServico()).getById(id);
        return "exibeChamado.xhtml?faces-redirect=true";
    }
    
    public String exibeMeuChamado(Long id){
        chamadoSelecionado = (new ChamadoServico()).getById(id);
        return "exibeMeuChamado.xhtml?faces-redirect=true";
    }
    
    public String atualizaChamado(String destino) throws IllegalArgumentException, Exception {
        try{
        ChamadoServico pra = new ChamadoServico();
        Chamado cha = pra.getById(chamadoSelecionado.getId());
        cha.setStatus(chamadoSelecionado.getStatus());
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/aaaa HH:mm");
	Date date = new Date();
	System.out.println(dateFormat.format(date));
        
        String novaDesc;
        novaDesc = chamadoSelecionado.getDescricao() + '\n';
        novaDesc += dateFormat.format(date) + ": " + this.texto;
        cha.setDescricao(novaDesc);
        cha.setAtribuido(chamadoSelecionado.getAtribuido());
        cha.setPrioridade(chamadoSelecionado.getPrioridade());

        if (chamadoSelecionado.getStatus().equals("Satisfeito")) {

            long diff = Calendar.getInstance().getTimeInMillis() - cha.getData().getTime();

            //long diffSeconds = diff / 1000 % 60;
            //long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            //long diffDays = diff / (24 * 60 * 60 * 1000);
            cha.setTempo_solucao((int) diffHours);
        }
        if (pra.atualizaChamado(cha)) {
            if (chamadoSelecionado.getStatus().equals("Satisfeito")) {
                adicionaMensagem("Chamado de número " + cha.getId() + " finalizado!", "destinoAviso");
            } else {
                adicionaMensagem("Chamado de número " + cha.getId() + " atualizado!", "destinoAviso");
            }
        } else {
            adicionaMensagem("Status não pode ser alterado!", "destinoAviso");
        }
        return destino;
        }catch(IllegalArgumentException e){
            adicionaMensagem("Escolha um chamado!","destinoAviso");
            return destino;
        }
    }
    
    public String finalizaChamado(String destino) throws Exception {
        ChamadoServico pra = new ChamadoServico();
        Chamado cha = pra.getById(chamadoSelecionado.getId());
        cha.setStatus("Satisfeito");
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/aaaa HH:mm");
	Date date = new Date();
	System.out.println(dateFormat.format(date));
        
        String novaDesc;
        novaDesc = chamadoSelecionado.getDescricao() + '\n';
        novaDesc += dateFormat.format(date) + ": " + this.texto;
        cha.setDescricao(novaDesc);
        cha.setAtribuido(chamadoSelecionado.getAtribuido());
        cha.setPrioridade(chamadoSelecionado.getPrioridade());

            long diff = Calendar.getInstance().getTimeInMillis() - cha.getData().getTime();

            //long diffSeconds = diff / 1000 % 60;
            //long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            //long diffDays = diff / (24 * 60 * 60 * 1000);
            cha.setTempo_solucao((int) diffHours);
            
        if (pra.atualizaChamado(cha)) {
                adicionaMensagem("Chamado de número " + cha.getId() + " finalizado!", "destinoAviso");
        } else {
            adicionaMensagem("Status não pode ser finalizado!", "destinoAviso");
        }
        return destino;
    }
    
    public List<String> getPrioridades(){
        List<String> prior = new ArrayList<>();
        prior.add("Baixa");
        prior.add("Média");
        prior.add("Alta");
        return prior;
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
        if(dinicio == null || dfim == null) return this.getChamados();
        return new ChamadoServico().chamadosEntreDatas(dinicio, dfim);
    }

    public List<Chamado> getChamadosEntreDatasStatusDivisao(Date dinicio, Date dfim, String status, String divisao){
        return new ChamadoServico().chamadosEntreDatasStatusDivisao(dinicio, dfim, status, divisao);
    }
    
    public List<Chamado> getChamadosEntreDatasDivisao(Date dinicio, Date dfim, String divisao){
        if(dinicio == null || dfim == null) return new ArrayList<>();
        return new ChamadoServico().chamadosEntreDatasDivisao(dinicio, dfim, divisao);
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
    
    public String filtrarLista(){
        chamFiltrados = this.getChamadosEntreDatas(dataInicial, dataFinal);
        return "relatorioUsuario";
    }
    
    public String filtrarListaDivisao(Divisao div){
        chamFiltradosDivisao = this.getChamadosEntreDatasDivisao(dataInicial, dataFinal, div.getNome());
        return "relatorioUsuario";
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
