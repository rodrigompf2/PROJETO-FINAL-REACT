package br.com.serratec.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.dto.AvaliacaoRequestDTO;
import br.com.serratec.dto.AvaliacaoResponseDTO;
import br.com.serratec.service.AvaliacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorProduto(@PathVariable UUID produtoId) {
        return ResponseEntity.ok(avaliacaoService.listarPorProduto(produtoId));
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> inserir(@Valid @RequestBody AvaliacaoRequestDTO dto) {
        AvaliacaoResponseDTO response = avaliacaoService.inserir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> editar(@PathVariable UUID id,@Valid @RequestBody AvaliacaoRequestDTO dto ) {
        AvaliacaoResponseDTO response = avaliacaoService.editar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        avaliacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}