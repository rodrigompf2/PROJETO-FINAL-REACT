package br.com.serratec.dto;

import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PedidoRequestDTO {

    @NotNull(message = "O ID do cliente não pode ser nulo.")
    private UUID clienteId;

    @NotEmpty(message = "O pedido deve conter pelo menos um item.")
    private List<ProdutoPedidoRequestDTO> itens;

    public static class ProdutoPedidoRequestDTO { 
        @NotNull
        private UUID produtoId;

        @NotNull @Positive
        private Integer quantidade;

        @NotNull @Positive
        private Double valorVenda;

        private Double desconto;
        
        public ProdutoPedidoRequestDTO() {}

        public UUID getProdutoId() {
            return produtoId;
        }
        public void setProdutoId(UUID produtoId) {
            this.produtoId = produtoId;
        }
        public Integer getQuantidade() {
            return quantidade;
        }
        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }
        public Double getValorVenda() {
            return valorVenda;
        }
        public void setValorVenda(Double valorVenda) {
            this.valorVenda = valorVenda;
        }
        public Double getDesconto() {
            return desconto;
        }
        public void setDesconto(Double desconto) {
            this.desconto = desconto;
        }
    }
    
    public PedidoRequestDTO() {}

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public List<ProdutoPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ProdutoPedidoRequestDTO> itens) {
        this.itens = itens;
    }
}