package br.com.serratec.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotaFiscalResponseDTO(UUID id, String numeroNota, LocalDateTime dataEmissao, String nomeCliente,
		Double valorTotal, Double impostos, String status) {
}
