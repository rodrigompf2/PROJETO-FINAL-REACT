package br.com.serratec.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.serratec.entity.Pedido;
import br.com.serratec.entity.ProdutoPedido;
import br.com.serratec.enums.StatusPedido;

public class PedidoResponseDTO {

	private UUID id;
	private StatusPedido status;
	private LocalDate dataPedido;
	private String nomeCliente;
	private List<ProdutoPedidoResponseDTO> itens;
	private Double valorTotal;

	public static class ProdutoPedidoResponseDTO {
		private String nomeProduto;
		private Integer quantidade;
		private Double subtotal;

		public ProdutoPedidoResponseDTO() {
		}

		public ProdutoPedidoResponseDTO(ProdutoPedido item) {
			this.nomeProduto = item.getProduto().getNome();
			this.quantidade = item.getQuantidade();
			this.subtotal = (item.getValorVenda() - (item.getDesconto() != null ? item.getDesconto() : 0.0))
					* item.getQuantidade();
		}

		public String getNomeProduto() {
			return nomeProduto;
		}

		public void setNomeProduto(String nomeProduto) {
			this.nomeProduto = nomeProduto;
		}

		public Integer getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(Integer quantidade) {
			this.quantidade = quantidade;
		}

		public Double getSubtotal() {
			return subtotal;
		}

		public void setSubtotal(Double subtotal) {
			this.subtotal = subtotal;
		}
	}

	public PedidoResponseDTO() {
	}

	public PedidoResponseDTO(Pedido pedido) {
		this.id = pedido.getId();
		this.status = pedido.getStatus();
		this.dataPedido = pedido.getDataPedido();
		this.nomeCliente = pedido.getCliente().getNome();

		this.itens = new ArrayList<>();
		this.valorTotal = 0.0;

		if (pedido.getItens() != null) {
			for (ProdutoPedido itemEntidade : pedido.getItens()) {
				ProdutoPedidoResponseDTO itemDTO = new ProdutoPedidoResponseDTO(itemEntidade);
				this.itens.add(itemDTO);
				this.valorTotal += itemDTO.getSubtotal();
			}
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public List<ProdutoPedidoResponseDTO> getItens() {
		return itens;
	}

	public void setItens(List<ProdutoPedidoResponseDTO> itens) {
		this.itens = itens;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
}