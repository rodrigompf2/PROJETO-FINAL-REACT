package br.com.serratec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import br.com.serratec.dto.ClienteRequestDTO;
import br.com.serratec.dto.ClienteResponseDTO;
import br.com.serratec.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes") 
public class ClienteController {

    @Autowired
    private ClienteService clienteService; 

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }
 
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> inserir(
            @Valid @RequestBody ClienteRequestDTO dto
    ) {
        ClienteResponseDTO clienteSalvo = clienteService.inserir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> editar(
            @PathVariable UUID id,           
            @Valid @RequestBody ClienteRequestDTO dto 
    ) {
        ClienteResponseDTO clienteAtualizado = clienteService.editar(id, dto);
        return ResponseEntity.ok(clienteAtualizado);
    }
}