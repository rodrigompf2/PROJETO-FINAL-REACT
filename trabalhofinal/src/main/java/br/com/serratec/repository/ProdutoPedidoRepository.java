package br.com.serratec.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.entity.ProdutoPedido;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, UUID> {

}
