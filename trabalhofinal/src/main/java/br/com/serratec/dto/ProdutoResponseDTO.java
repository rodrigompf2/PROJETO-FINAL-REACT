package br.com.serratec.dto;

import java.util.UUID;

import br.com.serratec.entity.Produto;

public record ProdutoResponseDTO(UUID id, String nome, Double valor, String nomeCategoria) {

	public ProdutoResponseDTO(Produto produto) {
		this(produto.getId(), produto.getNome(), produto.getValor(), produto.getCategoria().getNome());
	}
}
