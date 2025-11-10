package br.com.serratec.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.serratec.dto.NotaFiscalRequestDTO;
import br.com.serratec.dto.NotaFiscalResponseDTO;
import br.com.serratec.service.NotaFiscalService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/notas")
public class NotaFiscalController {

	@Autowired
	public NotaFiscalService fiscalService;
	
	@PostMapping
	public ResponseEntity<NotaFiscalResponseDTO> inserir (@Valid @RequestBody NotaFiscalRequestDTO fiscalRequestDTO){
		NotaFiscalResponseDTO reponse = fiscalService.InserirNota(fiscalRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(reponse);
	}
	
	@GetMapping
	public ResponseEntity<List<NotaFiscalResponseDTO>>listar (){
		return ResponseEntity.ok(fiscalService.listarNota());
	}
	
	@PutMapping
	public ResponseEntity<NotaFiscalResponseDTO> atualizar (@PathVariable UUID id, @RequestBody NotaFiscalRequestDTO dto){
		NotaFiscalResponseDTO reponse = fiscalService.atualizar(id, dto);
		return ResponseEntity.ok(reponse);
		
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        fiscalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

