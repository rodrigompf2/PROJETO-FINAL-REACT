package br.com.serratec.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.dto.CategoriaRequestDTO;
import br.com.serratec.dto.CategoriaResponseDTO;
import br.com.serratec.service.CategoriaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService service;
	
	@PostMapping
	public ResponseEntity<CategoriaResponseDTO> inserir (@Valid @RequestBody CategoriaRequestDTO categoriaResquestDTO){
		CategoriaResponseDTO response = service.inserirCategoria(categoriaResquestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public ResponseEntity<Set<CategoriaResponseDTO>> listar (){
		return ResponseEntity.ok(service.listar());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable UUID id, @RequestBody CategoriaRequestDTO dto) {

	    CategoriaResponseDTO response = service.atualizar(id, dto);
	    return ResponseEntity.ok(response);
	}
}
