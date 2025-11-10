package br.com.serratec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.serratec.dto.ClienteRequestDTO;
import br.com.serratec.dto.ClienteResponseDTO;
import br.com.serratec.entity.Cliente;
import br.com.serratec.exception.DataConflictException;
import br.com.serratec.repository.ClienteRepository;

@Service
public class ClienteService {
	
	private final ClienteRepository repository;
	
	public ClienteService(ClienteRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public List<ClienteResponseDTO> listar() {
	    List<Cliente> clientes = repository.findAll();
	    List<ClienteResponseDTO> dtos = new ArrayList<>();

	    for (Cliente cliente : clientes) {
	        dtos.add(new ClienteResponseDTO(cliente));
	    }
        return dtos;
	}
		
	@Transactional
	public ClienteResponseDTO inserir (ClienteRequestDTO dto) {
		
		if (repository.existsByCpf(dto.cpf())) {
            throw new DataConflictException("CPF já cadastrado.");
        }
        if (repository.existsByEmail(dto.email())) {
            throw new DataConflictException("Email já cadastrado.");
        }
		
		Cliente cliente = new Cliente();
		
		cliente.setNome(dto.nome());
		cliente.setEmail(dto.email());
	    cliente.setCpf(dto.cpf());
	    cliente.setTelefone(dto.telefone());
        cliente.setCep(dto.cep());
        cliente.setEndereco(dto.endereco());
		
		cliente = repository.save(cliente);
		
		return new ClienteResponseDTO(cliente);
	}

	@Transactional
	public ClienteResponseDTO editar(UUID id, ClienteRequestDTO dto) {
	    Cliente cliente = repository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Cliente não encontrado com esse id"));

	    cliente.setNome(dto.nome());
		cliente.setEmail(dto.email());
	    cliente.setCpf(dto.cpf());
	    cliente.setTelefone(dto.telefone());
        cliente.setCep(dto.cep());
        cliente.setEndereco(dto.endereco());
	    
	    cliente = repository.save(cliente);

	    return new ClienteResponseDTO(cliente);
	}
}