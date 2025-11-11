package br.com.serratec.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.serratec.entity.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, UUID> {

    List<Avaliacao> findByProdutoId(UUID produtoId);
}