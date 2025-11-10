package br.com.serratec.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.serratec.dto.AvaliacaoRequestDTO;
import br.com.serratec.dto.AvaliacaoResponseDTO;
import br.com.serratec.entity.Avaliacao;
import br.com.serratec.entity.Cliente;
import br.com.serratec.entity.Produto;
import br.com.serratec.repository.AvaliacaoRepository;
import br.com.serratec.repository.ClienteRepository;
import br.com.serratec.repository.ProdutoRepository;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDTO> listarPorProduto(UUID produtoId) {
        if (!produtoRepository.existsById(produtoId)) {
            throw new RuntimeException("Produto não encontrado");
        }
        
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByProdutoId(produtoId);
        
        List<AvaliacaoResponseDTO> dto = new ArrayList<>();
        for (Avaliacao avaliacao : avaliacoes) {
            dto.add(new AvaliacaoResponseDTO(avaliacao));
        }
        return dto;
    }
    
    @Transactional
    public AvaliacaoResponseDTO inserir(AvaliacaoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        
        Produto produto = produtoRepository.findById(dto.getProdutoId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setCliente(cliente);
        avaliacao.setProduto(produto);
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setDataAvaliacao(LocalDate.now());

        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);
        return new AvaliacaoResponseDTO(avaliacaoSalva);
    }

    @Transactional
    public AvaliacaoResponseDTO editar(UUID id, AvaliacaoRequestDTO dto) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Avaliação não encontrada."));

        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        Avaliacao avaliacaoAtualizada = avaliacaoRepository.save(avaliacao);
        return new AvaliacaoResponseDTO(avaliacaoAtualizada);
    }

    @Transactional
    public void deletar(UUID id) {
        if (!avaliacaoRepository.existsById(id)) {
            throw new RuntimeException("Avaliação não encontrada.");
        }
        avaliacaoRepository.deleteById(id);
    }
}