package br.com.serratec.dto;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AvaliacaoRequestDTO {

    @NotNull
    private UUID clienteId;

    @NotNull
    private UUID produtoId;

    @NotNull
    @Min(value = 1, message = "A nota deve ser no mínimo 1!")
    @Max(value = 5, message = "A nota deve ser no máximo 5!")
    private Integer nota;

    @Size(max = 200, message = "O comentário não pode exceder 200 caracteres!!!")
    private String comentario;
    
    public AvaliacaoRequestDTO() {}

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public UUID getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(UUID produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}