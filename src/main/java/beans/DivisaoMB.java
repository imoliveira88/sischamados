package beans;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import modelo.Divisao;
import servico.DivisaoServico;

@RequestScoped
@ManagedBean(name = "divisaoMB")
public class DivisaoMB extends Artificial implements Serializable{
    
    private Divisao divisao;
    private String nome;
    private int numero;
    private Divisao divisaoSelecionada;

    public DivisaoMB() {
        divisao = new Divisao();
        divisaoSelecionada = new Divisao();
    }

    public Divisao getDivisao() {
        return divisao;
    }

    public void setDivisao(Divisao divisao) {
        this.divisao = divisao;
    }

    public Divisao getDivisaoSelecionada() {
        return divisaoSelecionada;
    }

    public void setDivisaoSelecionada(Divisao divisaoSelecionada) {
        this.divisaoSelecionada = divisaoSelecionada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String salvar() throws Exception {
        DivisaoServico pra = new DivisaoServico();
        divisao.setNome(nome.toUpperCase());
        divisao.setNumero(numero);
        if(!pra.existeDivisao(this.divisao)){
            pra.salvar(divisao);
            adicionaMensagem("Divisão cadastrada com sucesso!","destinoAviso","SUCESSO!");
            return "cadDivisao";
        }
        else{
            adicionaMensagem("A divisão já está cadastrada!","destinoAviso","ERRO!");
            return "cadDivisao";
        }
    }
    
    public Divisao retornaDivisao(String div) throws Exception{
        return (new DivisaoServico()).retornaDivisao(div);
    }
    
    public List<Divisao> getDivisoes() throws Exception{
        return new DivisaoServico().todasDivisoes();
    }
    
    public List<Divisao> getPrestadores() throws Exception{
        return new DivisaoServico().divisoesPrestadoras();
    }
    
    public String excluir(Long id) throws Exception {
        DivisaoServico pra = new DivisaoServico();
        try {
            if (pra.deletarDivisao(id)) {
                adicionaMensagem("Divisão removida com sucesso!", "destinoAviso", "SUCESSO!");
            } else {
                adicionaMensagem("Divisão não pode ser removida, pois há colaboradores cadastrados nela! Caso queira excluí-la atualize primeiro as informações de seus colaboradores e, então, tente novamente!", "destinoAviso", "ERRO!");
            }
            return "cadDivisao";
        } catch (Exception e) {
            adicionaMensagem("Uma divisão precisa ser selecionada, antes de clicar em excluir!", "destinoAviso", "ERRO!");
            return "cadDivisao";
        }
    }
    
}
