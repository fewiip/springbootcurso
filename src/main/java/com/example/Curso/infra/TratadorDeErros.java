package com.example.Curso.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> tratador404() {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> tratador400(MethodArgumentNotValidException ex) {
		var errors = ex.getFieldErrors();
		// return ResponseEntity.badRequest().build();
		return ResponseEntity.badRequest().body(errors.stream().map(DadosErro::new));
	}

	public record DadosErro(String campo, String mensagem) {
		public DadosErro(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
}
