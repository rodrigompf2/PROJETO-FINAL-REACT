package br.com.serratec.entity;

import java.time.LocalDate; 
import java.util.List;
import java.util.UUID;

import br.com.serratec.enums.StatusPedido; 
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany; 
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoPedido> itens;
    
    @OneToMany(mappedBy = "pedido")
    private List<NotaFiscal> notasFiscais;

    private LocalDate dataPedido;

    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public List<ProdutoPedido> getItens() {
        return itens;
    }

    public void setItens(List<ProdutoPedido> itens) {
        this.itens = itens;
    }

    public LocalDate getDataPedido() { 
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) { 
        this.dataPedido = dataPedido;
    }
}