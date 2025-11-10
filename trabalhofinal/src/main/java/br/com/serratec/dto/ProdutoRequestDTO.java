package br.com.serratec.dto;


import java.util.UUID;

import br.com.serratec.entity.Produto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoRequestDTO {

	
	@NotBlank(message = "Campo nulo ou vazio!")
	private String nome;
	
	
	@DecimalMin(value = "0", inclusive = true)
    private Double valor;
	
	@NotNull
	private UUID idCategoria;
	
	public ProdutoRequestDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProdutoRequestDTO(Produto produto) {
		
		this.nome = produto.getNome();
		this.valor = produto.getValor();
		this.idCategoria = produto.getCategoria().getId();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public UUID getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(UUID idCategoria) {
		this.idCategoria = idCategoria;
	}
}
