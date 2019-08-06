
package beans;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.Chamado;
import modelo.Divisao;
import modelo.Pessoa;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import servico.ChamadoServico;
import servico.PessoaServico;


@ManagedBean(name = "chamadoMB")
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
    private BarChartModel barModel;
    private int maior;

    public ChamadoMB() {
        chamado = new Chamado();
        chamadoSelecionado = new Chamado();
        solicitante = new Pessoa();
        chamFiltrados = this.getChamados();
        chamFiltradosDivisao = new ArrayList<>();
        dataInicial = null;
        dataFinal = null;
    }
    
    @PostConstruct
    public void init() {
        createBarModels();
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
    
    public List listaProxStatusPrestador(String statusAtual){
        List lista = new ArrayList<>();
        lista.add("Iniciado");
        lista.add("Delineando");
        lista.add("Aguardando material");
        lista.add("Aguardando execução");
        lista.add("Executando");
        lista.add("Executado");
        
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

    public BarChartModel getBarModel() {
        this.createBarModel();
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
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
    
    public String exibeChamadoPrestador(Long id){
        chamadoSelecionado = (new ChamadoServico()).getById(id);
        return "exibeChamadoPrestador.xhtml?faces-redirect=true";
    }
    
    public String exibeMeuChamado(Long id){
        chamadoSelecionado = (new ChamadoServico()).getById(id);
        return "exibeMeuChamado.xhtml?faces-redirect=true";
    }
    
    public String atualizaChamado(String destino) throws IllegalArgumentException, Exception {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            String historico;
            ChamadoServico pra = new ChamadoServico();
            Chamado cha = pra.getById(chamadoSelecionado.getId());
            historico = cha.getHistorico();
            cha.setStatus(chamadoSelecionado.getStatus());

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            System.out.println(dateFormat.format(date));

            String novaDesc;
            if(this.texto.equals("")) novaDesc = chamadoSelecionado.getDescricao();
            else{
                novaDesc = chamadoSelecionado.getDescricao();
                novaDesc += "<br/><b>" + dateFormat.format(date) + " " + session.getAttribute("usuario").toString() + "</b> " +  this.texto;
            }
            cha.setDescricao(novaDesc);
            cha.setAtribuido(chamadoSelecionado.getAtribuido());
            cha.setPrioridade(chamadoSelecionado.getPrioridade());
                        
            if (semAlteracao(cha) == 1) {
                historico += "<br/><b>" + dateFormat.format(date) + " " + session.getAttribute("usuario").toString() + "</b> alterou status para " + chamadoSelecionado.getStatus() + ", atribuído para " + chamadoSelecionado.getAtribuido() + " e prioridade para " + chamadoSelecionado.getPrioridade();
                cha.setHistorico(historico);
            } else if (semAlteracao(cha) == 2) {
                historico += "<br/><b>" + dateFormat.format(date) + " " + session.getAttribute("usuario").toString() + "</b> atualizou a descrição";
                cha.setHistorico(historico);
            }
            
            this.texto = "";

            if (chamadoSelecionado.getStatus().equals("Satisfeito") && semAlteracao(cha)!=1) {

                long diff = Calendar.getInstance().getTimeInMillis() - cha.getData().getTime();
                long diffHours = diff / (60 * 60 * 1000);
                cha.setTempo_solucao((int) diffHours);
            }
            if (pra.atualizaChamado(cha)) {
                if (chamadoSelecionado.getStatus().equals("Satisfeito") && semAlteracao(cha)!=1) {
                    adicionaMensagem("Chamado de número " + cha.getId() + " finalizado!", "destinoAviso","SUCESSO!");
                } else {
                    adicionaMensagem("Chamado de número " + cha.getId() + " atualizado!", "destinoAviso", "SUCESSO!");
                }
            } else {
                adicionaMensagem("Status não pode ser alterado!", "destinoAviso","ERRO!");
            }
            chamFiltrados = this.getChamadosEntreDatas(dataInicial, dataFinal);
            return destino;
        } catch (IllegalArgumentException e) {
            adicionaMensagem("Escolha um chamado!", "destinoAviso", "ERRO!");
            return destino;
        }
    }
    
    public int semAlteracao(Chamado c) throws NullPointerException {
        try {
            if (c.getPrioridade().equals(chamadoSelecionado.getPrioridade()) && c.getStatus().equals(chamadoSelecionado.getStatus()) && c.getAtribuido().equals(chamadoSelecionado.getAtribuido())) {
                if (texto.equals("")) {
                    return 0; //Nenhuma alteração
                } else {
                    return 2; //Houve alteração na descrição
                }
            }
            return 1; //Houve alteração na prioridade, status ou no atribuído
        } catch (Exception e) {
            if(c.getAtribuido() == null){
                if(texto.equals("")) return 0;
                else return 2;
            }
            return 0; //caso tenha sido lançada uma NullPointerException, será considerado que nada foi alterado
        }
    }

    public String finalizaChamado(String destino) throws Exception {
        ChamadoServico pra = new ChamadoServico();
        Chamado cha = pra.getById(chamadoSelecionado.getId());
        cha.setStatus("Satisfeito");

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        String historico = cha.getHistorico();;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        String novaDesc;
        if(this.texto.equals("")) novaDesc = chamadoSelecionado.getDescricao();
            else{
                novaDesc = chamadoSelecionado.getDescricao();
                novaDesc += "<br/><b>" + dateFormat.format(date) + " " + session.getAttribute("usuario").toString() + "</b> " +  this.texto;
            }
        cha.setDescricao(novaDesc);
        cha.setAtribuido(chamadoSelecionado.getAtribuido());
        cha.setPrioridade(chamadoSelecionado.getPrioridade());
        
        this.texto = "";

        long diff = Calendar.getInstance().getTimeInMillis() - cha.getData().getTime();
        long diffHours = diff / (60 * 60 * 1000);
        cha.setTempo_solucao((int) diffHours);

        if(semAlteracao(cha) == 1){
                historico += "<br/><b>" + dateFormat.format(date) + " " + session.getAttribute("usuario").toString() + "</b> alterou status para " + chamadoSelecionado.getStatus() + ", atribuído para " + chamadoSelecionado.getAtribuido() + " e prioridade para " + chamadoSelecionado.getPrioridade();
                cha.setHistorico(historico);
        }else if(semAlteracao(cha) == 2){
                historico += "<br/><b>" + dateFormat.format(date) + " " + session.getAttribute("usuario").toString() + "</b> atualizou a descrição";
                cha.setHistorico(historico);
        }

        if (pra.atualizaChamado(cha)) {
            adicionaMensagem("Chamado de número " + cha.getId() + " finalizado!", "destinoAviso", "SUCESSO!");
        } else {
            adicionaMensagem("Status não pode ser finalizado!", "destinoAviso", "ERRO!");
        }
        return destino;
    }
    
    public List<String> getPrioridades(){
        List<String> prior = new ArrayList<>();
        prior.add("Emergencial");
        prior.add("Preferencial");
        prior.add("Normal");
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
        if(st.equals("TODOS") || st.equals("")) return this.getChamadosPorDivisao(div);
        return new ChamadoServico().chamadosDivisaoStatus(div,st);
    }
    
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        List<ChartSeries> lista = new ArrayList<>();
        maior = 0;
        
        int aux;
        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        
        Divisao div = ((Pessoa)session.getAttribute("usuario")).getDivisao();
                
        for(int i=0; i<listaStatus().size(); i++){
            lista.add(new ChartSeries());
            lista.get(i).setLabel(listaStatus().get(i).toString());
            if(div != null) aux = this.getChamadosParaDivisaoStatus(div.toString(),listaStatus().get(i).toString()).size();
            else aux = 0;
            if(aux > maior) maior = aux;
            lista.get(i).set("", aux);
            model.addSeries(lista.get(i));
        }
 
        return model;
    }
 
    private void createBarModels() {
        createBarModel();
    }
 
    private void createBarModel() {
        barModel = initBarModel();
 
        barModel.setTitle("Quantidade de chamados por status");
        barModel.setLegendPosition("ne");
        barModel.setBarWidth(20);
 
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Quantidade");
        
        yAxis.setMin(0);
        yAxis.setMax(maior);
    }
 
    
    public List<Chamado> getChamadosParaDivisaoStatus(String div, String st){
        if(st.equals("TODOS") || st.equals("")) return new ChamadoServico().chamadosParaDivisao(div);
        return new ChamadoServico().chamadosParaDivisaoStatus(div,st);
    }
    
    public List<Chamado> getChamadosEntreDatas(Date dinicio, Date dfim){
        if(dinicio == null || dfim == null) return this.getChamados();
        return new ChamadoServico().chamadosEntreDatas(dinicio, dfim);
    }

    public List<Chamado> getChamadosEntreDatasStatusDivisao(Date dinicio, Date dfim, String status, String divisao){
        return new ChamadoServico().chamadosEntreDatasStatusDivisao(dinicio, dfim, status, divisao);
    }
    
    public List<Chamado> getChamadosEntreDatasDivisao(Date dinicio, Date dfim, String divisao) throws NullPointerException {
        try {
            if (dinicio == null || dfim == null) {
                return new ArrayList<>();
            }
            return new ChamadoServico().chamadosEntreDatasDivisao(dinicio, dfim, divisao);
        } catch (Exception e) {
            return new ArrayList<>();
        }
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
    
    public double mediaResolucao(String div){
	return (new ChamadoServico()).chamadosMedia(div);
    }
    
    public String filtrarListaDivisao(Divisao div){
        chamFiltradosDivisao = this.getChamadosEntreDatasDivisao(dataInicial, dataFinal, div.getNome());
        return "relatorioUsuario";
    }
    
    public String excluir(Long id) throws Exception {
        ChamadoServico pra = new ChamadoServico();
        try {
            if (pra.excluir(id)) {
                adicionaMensagem("Chamado removido com sucesso!", "destinoAviso", "SUCESSO!");
            } else {
                adicionaMensagem("Chamado não pode ser removido!", "destinoAviso", "ERRO!");
            }
            chamFiltrados = this.getChamadosEntreDatas(dataInicial, dataFinal);
            return "relatorios";
        } catch (Exception e) {
            adicionaMensagem("Um chamado precisa ser selecionado, antes de clicar em excluir!", "destinoAviso", "ERRO!");
            return "relatorios";
        }
    }
    
    
    public String salvar(Long idsolicitante){
        chamado = new Chamado();
        chamadoSelecionado = new Chamado();
        solicitante = new Pessoa();
        chamFiltrados = this.getChamados();
        chamFiltradosDivisao = new ArrayList<>();
        dataInicial = null;
        dataFinal = null;
        titulo = "";
        texto = "";
        descricao = "";
        
        
        chamado.setData(Calendar.getInstance().getTime());
        chamado.setStatus("Iniciado");
        chamado.setDescricao(descricao);
        chamado.setTitulo(titulo);
        chamado.setSolicitado(solicitado);
        chamado.setPrioridade(prioridade);
        chamado.setSolicitante((new PessoaServico()).getById(idsolicitante));
        chamado.setHistorico("");
        ChamadoServico chamadoDAO = new ChamadoServico();
        chamadoDAO.salvar(chamado);
        return "meusChamados";
    }
    
}
