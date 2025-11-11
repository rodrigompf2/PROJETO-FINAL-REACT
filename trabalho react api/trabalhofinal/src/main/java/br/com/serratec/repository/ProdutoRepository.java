package br.com.serratec.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, UUID>{
	
	boolean existsByNome(String nome);
    
}
