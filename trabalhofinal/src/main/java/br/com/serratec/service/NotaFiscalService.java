package br.com.serratec.service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.NotaFiscalRequestDTO;
import br.com.serratec.dto.NotaFiscalResponseDTO;
import br.com.serratec.entity.NotaFiscal;
import br.com.serratec.entity.Pedido;
import br.com.serratec.enums.StatusNota;
import br.com.serratec.exception.RegraNegocioException;
import br.com.serratec.repository.NotaFiscalRepository;
import br.com.serratec.repository.PedidoRepository;

@Service
public class NotaFiscalService {

	@Autowired
	public NotaFiscalRepository fiscalRepository;

	@Autowired
	public PedidoRepository pedidoRepository;

	public NotaFiscalResponseDTO InserirNota(NotaFiscalRequestDTO dto) {
		Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
				.orElseThrow(() -> new RegraNegocioException("Pedido com if" + dto.getPedidoId() + "não encontrado."));

		double valorTotal = pedido.getItens().stream()
				.mapToDouble(item -> item.getQuantidade() * item.getProduto().getValor()).sum();

		String numeroNota = "NF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

		NotaFiscal notaFiscal = new NotaFiscal();
		notaFiscal.setNumeroNota(numeroNota);
		notaFiscal.setDataEmissao(LocalDateTime.now());
		notaFiscal.setPedido(pedido);
		notaFiscal.setImpostos(dto.getImpostos());
		notaFiscal.setValorTotal(valorTotal);
		notaFiscal.setStatus(StatusNota.EMITIDA);

		fiscalRepository.save(notaFiscal);

		return new NotaFiscalResponseDTO(notaFiscal.getId(), notaFiscal.getNumeroNota(), notaFiscal.getDataEmissao(),
				pedido.getCliente().getNome(), notaFiscal.getValorTotal(), notaFiscal.getImpostos(),
				notaFiscal.getStatus().name());
	}

	public List<NotaFiscalResponseDTO> listarNota() {
		List<NotaFiscal> notas = fiscalRepository.findAll();

		return notas.stream()
				.map(nota -> new NotaFiscalResponseDTO(nota.getId(), nota.getNumeroNota(), nota.getDataEmissao(),
						nota.getPedido().getCliente().getNome(), nota.getValorTotal(), nota.getImpostos(),
						nota.getStatus().name()))
				.toList();

	}

	public NotaFiscalResponseDTO atualizar(UUID id, NotaFiscalRequestDTO dto) {
		NotaFiscal nota = fiscalRepository.findById(id)
				.orElseThrow(() -> new RegraNegocioException("Nota fiscal com ID " + id + " não encontrada."));

		nota.setImpostos(dto.getImpostos());
		nota.setStatus(StatusNota.EMITIDA);

		fiscalRepository.save(nota);

		return new NotaFiscalResponseDTO(nota.getId(), nota.getNumeroNota(), nota.getDataEmissao(),
				nota.getPedido().getCliente().getNome(), nota.getValorTotal(), nota.getImpostos(),
				nota.getStatus().name());
	}
	
	public void deletar(UUID id) {
	    NotaFiscal nota = fiscalRepository.findById(id)
	        .orElseThrow(() -> new RegraNegocioException("Nota fiscal com ID " + id + " não encontrada."));

	    nota.setStatus(StatusNota.CANCELADA);
	    fiscalRepository.save(nota);
	}


}