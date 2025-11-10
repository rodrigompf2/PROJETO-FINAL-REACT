package br.com.serratec.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.CategoriaRequestDTO;
import br.com.serratec.dto.CategoriaResponseDTO;
import br.com.serratec.entity.Categoria;
import br.com.serratec.exception.DataConflictException;
import br.com.serratec.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public CategoriaResponseDTO inserirCategoria(CategoriaRequestDTO dto) {
		
		String nomeCategoria = dto.getNome();
        if (repository.existsByNome(nomeCategoria)) {
            throw new DataConflictException("Categoria com esse nome já cadastrada.");
        }
		Categoria categoria = new Categoria();
		categoria.setNome(dto.getNome());

		categoria = repository.save(categoria);

		return new CategoriaResponseDTO(categoria);

	}

	public Set<CategoriaResponseDTO> listar() {
		List<Categoria> categorias = repository.findAll();
		Set<CategoriaResponseDTO> categoriaresponse = new HashSet<>();

		for (Categoria categoria : categorias) {
			categoriaresponse.add(new CategoriaResponseDTO(categoria));
		}
		return categoriaresponse;
	}

	public CategoriaResponseDTO atualizar(UUID id, CategoriaRequestDTO dto) {
		Optional<Categoria> categoriaOptional = repository.findById(id);
		if (categoriaOptional.isEmpty()) {
			throw new RuntimeException("Categoria não encontrada");
		}

		Categoria categoria = categoriaOptional.get();
		categoria.setNome(dto.getNome());
		categoria.setId(id);

		Categoria categoriaAtualizada = repository.save(categoria);
		return new CategoriaResponseDTO(categoriaAtualizada);
	}
}
