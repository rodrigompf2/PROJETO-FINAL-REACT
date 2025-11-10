package br.com.serratec.dto;

import java.util.UUID;

import br.com.serratec.entity.Categoria;

public record CategoriaResponseDTO(UUID id, String nome) {

	public CategoriaResponseDTO(Categoria categoria) {
		this(categoria.getId(),categoria.getNome());
	}
}
