package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "TB_CIVIL")
@PrimaryKeyJoinColumn(name = "ID_CIVIL")
public class Civil extends Pessoa {
    private static final long serialVersionUID = 1L;
    
    public Civil(String nome, String senha, String documento){
        super(nome,senha,documento); 
        this.pedidos = new ArrayList<>();   
        this.cartao = cartao;
    }
    
    public Civil(){
        this.pedidos = new ArrayList<>();
    }
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CARTAO", referencedColumnName = "ID_CARTAO")
    private Cartao cartao;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
    
    public char tipo(){
        return 'C';
    }
    
}
