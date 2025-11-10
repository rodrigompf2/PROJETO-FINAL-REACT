package br.com.serratec.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID>{

	boolean existsByNome(String nome);

}
