package br.com.serratec.dto;

import java.util.UUID;

import br.com.serratec.entity.NotaFiscal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class NotaFiscalRequestDTO {

    @NotNull(message = "O ID do pedido é obrigatório.")
    private UUID pedidoId;

    @NotNull(message = "O valor dos impostos é obrigatório.")
    @Positive(message = "O valor dos impostos deve ser positivo.")
    private Double impostos;
    
    public NotaFiscalRequestDTO() {
		// TODO Auto-generated constructor stub
	}
    
    public NotaFiscalRequestDTO(NotaFiscal notaFiscal) {
        this.pedidoId = notaFiscal.getPedido().getId();
        this.impostos = notaFiscal.getImpostos();
    }

    public UUID getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(UUID pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Double getImpostos() {
        return impostos;
    }

    public void setImpostos(Double impostos) {
        this.impostos = impostos;
    }
}
