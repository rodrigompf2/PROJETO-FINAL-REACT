package br.com.serratec.dto;


import br.com.serratec.entity.Categoria;
import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDTO {

	@NotBlank(message = "Campo nulo ou vazio!")
	private String nome;
	

	public CategoriaRequestDTO() {
	}

	public CategoriaRequestDTO(Categoria categoria) {
		
		this.nome = categoria.getNome();
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
}
