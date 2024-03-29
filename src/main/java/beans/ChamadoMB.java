package beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.Chamado;
import modelo.Divisao;
import modelo.Pessoa;
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
    private List<Integer> quantidades;
    private String atribuido;
    private String texto;

    public ChamadoMB() throws Exception {
        chamado = new Chamado();
        //chamadoSelecionado = new Chamado();
        solicitante = new Pessoa();
        chamFiltrados = this.getChamados();
        chamFiltradosDivisao = new ArrayList<>();
        dataInicial = null;
        dataFinal = null;
        quantidades = this.criaEstatistica();
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

    public List<Integer> getQuantidades() {
        return quantidades;
    }

    public void setQuantidades(List<Integer> quantidades) {
        this.quantidades = quantidades;
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

    public String finalizaChamado(Long id) throws Exception{
        Chamado cha = (new ChamadoServico()).getById(id);
        cha.setStatus("Satisfeito");
        if ((new ChamadoServico()).atualizaChamado(cha)) {
                if (chamadoSelecionado.getStatus().equals("Satisfeito") && semAlteracao(cha)!=1) {
                    adicionaMensagem("Chamado de número " + cha.getId() + " finalizado!", "destinoAviso","SUCESSO!");
                } else {
                    adicionaMensagem("Chamado de número " + cha.getId() + " atualizado!", "destinoAviso", "SUCESSO!");
                }
            } else {
                adicionaMensagem("Status não pode ser alterado!", "destinoAviso","ERRO!");
            }
        return "";
    }
    
    public String atualizaChamado(String destino) throws IllegalArgumentException, Exception {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            String historico;
            ChamadoServico pra = new ChamadoServico();
            Chamado cha = chamadoSelecionado;
            status = chamadoSelecionado.getStatus();
            historico = cha.getHistorico();

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            
            String novaDesc;
            if(this.texto.equals("")) novaDesc = chamadoSelecionado.getDescricao();
            else{
                novaDesc = chamadoSelecionado.getDescricao();
                novaDesc += "<br/><b>" + dateFormat.format(date) + " " + session.getAttribute("usuario").toString() + "</b> " +  this.texto;
            }
           
            String auxStatus, auxAtribuido, auxPrioridade;
            if(status == null) auxStatus = "nulo";
            else auxStatus = status;
            
            if(atribuido == null) auxAtribuido = "nulo";
            else auxAtribuido = atribuido;
            
            if(prioridade == null) auxPrioridade = "nulo";
            else auxPrioridade = prioridade;
            
                                    
            if (semAlteracao(cha) == 1) {
                historico += "<br/><b>" + dateFormat.format(date) + " " + session.getAttribute("usuario").toString() + "</b> alterou status para " + auxStatus + ", atribuído para " + auxAtribuido + " e prioridade para " + auxPrioridade;
                cha.setHistorico(historico);
            } else if (semAlteracao(cha) == 2) {
                historico += "<br/><b>" + dateFormat.format(date) + " " + session.getAttribute("usuario").toString() + "</b> atualizou a descrição";
                cha.setHistorico(historico);
            }
            
            this.texto = "";
                        
            cha.setDescricao(novaDesc);
            
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
            quantidades = this.criaEstatistica();
            return destino;
        } catch (IllegalArgumentException e) {
            adicionaMensagem("Escolha um chamado!", "destinoAviso", "ERRO!");
            return destino;
        }
    }
    
    public String encaminhaChamado(String destino){
        return destino;
    }
    
    public String encaminhaAtualChamado() throws Exception{
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        ChamadoServico pra = new ChamadoServico();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        
        chamadoSelecionado.setHistorico(chamadoSelecionado.getHistorico() + "<br/><b>" + dateFormat.format(date) + " " + session.getAttribute("usuario").toString() + "</b> encaminhou o chamado para " + chamadoSelecionado.getSolicitado());
        
        if (pra.atualizaChamado(chamadoSelecionado)) adicionaMensagem("Chamado de número " + chamadoSelecionado.getId() + " encaminhado!", "destinoAviso","SUCESSO!");
        else adicionaMensagem("Chamado não pôde ser encaminhado!", "destinoAviso","ERRO!");
        quantidades = this.criaEstatistica();
        return "chamadosParaDivisao.xhtml";
    }
    
    public int semAlteracao(Chamado c) throws NullPointerException {
        try {
           
            if (chamadoSelecionado.getAtribuido() == null) {
                if (c.getPrioridade().equals(prioridade) && c.getStatus().equals(status) && c.getAtribuido() == null) {
                    if (texto.equals("")) {
                        return 0; //Nenhuma alteração
                    } else {
                        return 2; //Houve alteração na descrição
                    }
                }
                return 1; //Houve alteração na prioridade, status ou no atribuído
            } else {
                if (c.getPrioridade().equals(prioridade) && c.getStatus().equals(status) && c.getAtribuido().equals(atribuido)) {
                    if (texto.equals("")) {
                        return 0; //Nenhuma alteração
                    } else {
                        return 2; //Houve alteração na descrição
                    }
                }
                return 1; //Houve alteração na prioridade, status ou no atribuído
            }
        } catch (Exception e) {
            if (c.getAtribuido() == null) {
                if (texto.equals("")) {
                    return 0;
                } else {
                    return 2;
                }
            }
            return 0; //caso tenha sido lançada uma NullPointerException, será considerado que nada foi alterado
        }
    }
    
    public List<String> getPrioridades(){
        List<String> prior = new ArrayList<>();
        prior.add("Emergencial");
        prior.add("Preferencial");
        prior.add("Normal");
        return prior;
    }
    
    public List<Chamado> getChamadosPorStatus(String status) throws Exception{
        return new ChamadoServico().chamadosStatus(status);
    }
    
    public List<Chamado> getChamadosPorDivisao(String div) throws NullPointerException, Exception{
        return new ChamadoServico().chamadosDivisao("da",div);
    }
    
    public List<Chamado> getChamadosParaDivisao(String div) throws NullPointerException, Exception{
        return new ChamadoServico().chamadosDivisao("para",div);
    }
    
    public List<Chamado> getChamadosPorDivisaoStatus(String div, String st) throws NullPointerException, Exception{
        if(st.equals("TODOS") || st.equals("")) return this.getChamadosPorDivisao(div);
        return new ChamadoServico().chamadosDivisaoStatus("da",div,st);
    }
    
    private List<Chamado> extraiChamadosStatus(List<Chamado> lista, String status){
        List<Chamado> aux = new ArrayList<>();
        if(lista.isEmpty()) return aux;
        for(int i=0; i<lista.size(); i++){
            if(lista.get(i).getStatus().equals(status)) aux.add(lista.get(i));
        }
        
        return aux;
    }
    
    private List<Integer> criaEstatistica() throws Exception {
        List<Integer> quant = new ArrayList<>();
        List<Chamado> chamadosDivisao;
        
        int aux;
        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Pessoa usu = (Pessoa) session.getAttribute("usuario");
        
        if(usu.getTipo() == 'A') return quant;
        
        Divisao div = usu.getDivisao();
        if (div.isPrestadora()) {
            chamadosDivisao = this.getChamadosParaDivisao(div.toString());
            if (!chamadosDivisao.isEmpty()) {
                for (int i = 0; i < listaStatus().size(); i++) {
                    aux = this.extraiChamadosStatus(chamadosDivisao, listaStatus().get(i).toString()).size(); //Filtra por cada status
                    quant.add(aux);
                }
            }
        }

        return quant;
    }
 
    
    public List<Chamado> getChamadosParaDivisaoStatus(String div, String st) throws NullPointerException, Exception{
        if(st.equals("TODOS") || st.equals("")) return new ChamadoServico().chamadosDivisao("para",div);
        return new ChamadoServico().chamadosDivisaoStatus("para",div,st);
    }
    
    public List<Chamado> getChamadosEntreDatas(Date dinicio, Date dfim) throws Exception{
        if(dinicio == null || dfim == null) return this.getChamados();
        return new ChamadoServico().chamadosEntreDatas(dinicio, dfim);
    }

    public List<Chamado> getChamadosEntreDatasStatusDivisao(Date dinicio, Date dfim, String status, String divisao) throws Exception{
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
    
    public List<Chamado> getChamadosEntreDatasStatus(Date dinicio, Date dfim, String status) throws Exception{
        return new ChamadoServico().chamadosEntreDatasStatus(dinicio, dfim, status);
    }
    
    public List<Chamado> getChamados() throws Exception{
        return (new ChamadoServico()).todosChamadosData();
        //return new ChamadoServico().todosChamados();
    }
    
    public String filtrarLista() throws Exception{
        chamFiltrados = this.getChamadosEntreDatas(dataInicial, dataFinal);
        return "relatorios";
    }
    
    public String filtrarListaDivisao(Divisao div){
        chamFiltradosDivisao = this.getChamadosEntreDatasDivisao(dataInicial, dataFinal, div.getNome());
        if(div.isPrestadora()) return "logado/prestadora/relatorioUsuario.xhtml";
        else return "relatorioUsuario";
    }
    
    public String excluir(Long id) throws Exception {
        ChamadoServico pra = new ChamadoServico();
        try {
            pra.excluir(id);
            adicionaMensagem("Chamado removido com sucesso!", "destinoAviso", "SUCESSO!");
            chamFiltrados = this.getChamadosEntreDatas(dataInicial, dataFinal);
            return "relatorios";
        } catch (Exception e) {
            adicionaMensagem("Um chamado precisa ser selecionado, antes de clicar em excluir!", "destinoAviso", "ERRO!");
            return "relatorios";
        }
    }
    
    
    public String salvar(Long idsolicitante) throws Exception{
        chamado = new Chamado();
        chamadoSelecionado = new Chamado();
             
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
        
        boolean prestadora = chamado.getSolicitante().getDivisao().isPrestadora();
        
        titulo = "";
        descricao = "";
        quantidades = this.criaEstatistica();
        if(prestadora) return "logado/prestadora/chamadosParaDivisao.xhtml";
        else return "logado/usuario/meusChamados.xhtml";
    }
    
}