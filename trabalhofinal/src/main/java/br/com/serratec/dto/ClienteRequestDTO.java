package br.com.serratec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
    
    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    @Size(min = 2, max = 100)
    String nome,

    @NotBlank
    @Email(message = "Formato de e-mail inválido.")
    String email,

    @NotBlank
    @Size(max = 20)
    String cpf,

    @NotBlank
    @Size(max = 14)
    String telefone,
    
    String cep,
    String endereco
) {
}