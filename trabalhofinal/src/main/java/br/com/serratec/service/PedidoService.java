package br.com.serratec.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.serratec.dto.PedidoRequestDTO;
import br.com.serratec.dto.PedidoResponseDTO;
import br.com.serratec.entity.Cliente;
import br.com.serratec.entity.Pedido;
import br.com.serratec.entity.Produto;
import br.com.serratec.entity.ProdutoPedido;
import br.com.serratec.enums.StatusPedido;
import br.com.serratec.exception.PedidoException;
import br.com.serratec.repository.ClienteRepository;
import br.com.serratec.repository.PedidoRepository;
import br.com.serratec.repository.ProdutoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository; 

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public PedidoResponseDTO inserir(PedidoRequestDTO dto) {
        
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new PedidoException("Cliente não encontrado!"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDate.now());
        pedido.setStatus(StatusPedido.PENDENTE); 

        List<ProdutoPedido> itens = new ArrayList<>();
        
        for (PedidoRequestDTO.ProdutoPedidoRequestDTO itemDTO : dto.getItens()) { 
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                .orElseThrow(() -> new PedidoException("Produto não encontrado: " + itemDTO.getProdutoId()));
            
            ProdutoPedido produtoPedido = new ProdutoPedido();
            produtoPedido.setPedido(pedido);
            produtoPedido.setProduto(produto);

            produtoPedido.setQuantidade(itemDTO.getQuantidade());
            produtoPedido.setValorVenda(itemDTO.getValorVenda());
            produtoPedido.setDesconto(itemDTO.getDesconto() != null ? itemDTO.getDesconto() : 0.0);
            
            itens.add(produtoPedido);
        }

        pedido.setItens(itens);
        
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return new PedidoResponseDTO(pedidoSalvo);
    }

    @Transactional
    public List<PedidoResponseDTO> listar() {
        List<Pedido> pedidos = pedidoRepository.findAll();       
        List<PedidoResponseDTO> dtos = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            dtos.add(new PedidoResponseDTO(pedido));
        }
        return dtos;
    }
    
    @Transactional
    public PedidoResponseDTO editar(UUID id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new PedidoException("Pedido não encontrado!"));
        
        pedido.setStatus(novoStatus);
        
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return new PedidoResponseDTO(pedidoAtualizado);
    }        
}