package com.example.Curso.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Curso.remedio.DadosCadastroRemedio;
import com.example.Curso.remedio.Remedio;
import com.example.Curso.remedio.RemedioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/remedios")
public class RemedioController {

	// chamado injecao de dependencias
	// asim eu posso usar os metodos ja implementados pelo repository
	@Autowired
	private RemedioRepository repository;

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastroRemedio dados) {
		System.out.println(dados);
		repository.save(new Remedio(dados));
	}

	/*
	 * @GetMapping
	 * public List<Remedio> listar() {
	 * 
	 * }
	 */
}
