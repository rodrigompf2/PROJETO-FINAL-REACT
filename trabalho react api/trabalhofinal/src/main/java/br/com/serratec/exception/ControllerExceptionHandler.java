package br.com.serratec.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> erros = new ArrayList<>();

		for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
			erros.add(erro.getField() + "-" + erro.getDefaultMessage());
		}

		ErroResposta erroResposta = new ErroResposta(status.value(), "Existem campos inválidos", LocalDateTime.now(),
				erros);

		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());

		ErroResposta erroResposta = new ErroResposta(status.value(), "Existem campos inválidos", LocalDateTime.now(),
				erros);

		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation( DataIntegrityViolationException ex) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(),
				"Violação de integridade no banco de dados:", LocalDateTime.now(), erros);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);
		
		
	}
	

	@ExceptionHandler(HttpClientErrorException.class)
	protected ResponseEntity<Object>handleHttpClientErrorException(HttpClientErrorException ex){
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErroResposta erroResposta = new ErroResposta(HttpStatus.NOT_FOUND.value(),
				"Cep não encontrado", LocalDateTime.now(), erros);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResposta);
		
	}
	
	@ExceptionHandler(EnumException.class)
	protected ResponseEntity<Object> handleEnumException(EnumException ex) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Status não definido:",
				LocalDateTime.now(), erros);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);

	}
	
	@ExceptionHandler(ClienteException.class)
	protected ResponseEntity<Object> handleClienteException(ClienteException ex) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Cliente não encontrado:",
				LocalDateTime.now(), erros);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);

	}
	
	@ExceptionHandler(ProdutoException.class)
	protected ResponseEntity<Object> handleProdutoException(ProdutoException ex) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Produto não encontrado:",
				LocalDateTime.now(), erros);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);

	}
	
	@ExceptionHandler(PedidoException.class)
	protected ResponseEntity<Object> handlePedidoException(PedidoException ex) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Pedido não encontrado:",
				LocalDateTime.now(), erros);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);

	}
	
	@ExceptionHandler(RegraNegocioException.class)
	protected ResponseEntity<Object> handleRegraNegocioException(RegraNegocioException ex) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Pedido vazio ou desconto inválido:",
				LocalDateTime.now(), erros);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);

	}
	
	@ExceptionHandler(CepNaoEncontradoExeption.class)
	protected ResponseEntity<Object> handleCepNaoEncontradoExeption(CepNaoEncontradoExeption ex) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Pedido não encontrado:",
				LocalDateTime.now(), erros);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);

	}
	
	
	@ExceptionHandler(DataConflictException.class)
    public ResponseEntity<ErroResposta> handleDataConflictException(DataConflictException ex) {
        
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        ErroResposta erroResposta = new ErroResposta(HttpStatus.CONFLICT.value(), 
                "Conflito de Dados",LocalDateTime.now(),erros);                      

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erroResposta);
    
    }
	
	@ExceptionHandler(UsuarioException.class)
	protected ResponseEntity<Object> handleUsuarioException(UsuarioException ex) {
		
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErroResposta erroResposta = new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), 
				"Existem campos inválidos", LocalDateTime.now(),
				erros);
	
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erroResposta);
	}
}
