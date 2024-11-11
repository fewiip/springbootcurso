package com.example.Curso.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Curso.remedio.DadosAtualizarRemedio;
import com.example.Curso.remedio.DadosCadastroRemedio;
import com.example.Curso.remedio.DadosListagemRemedio;
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

	@GetMapping
	public List<DadosListagemRemedio> listar() {
		return repository.findAllByAtivoTrue().stream().map(DadosListagemRemedio::new).toList();

	}

	@PutMapping
	@Transactional
	public void atualizar(@RequestBody @Valid DadosAtualizarRemedio dados) {
		var remedio = repository.getReferenceById(dados.id());
		// acessa o banco de dados e pega os dados pelo ID
		remedio.atualizarInformacoes(dados);
	}

	@DeleteMapping("/{id}")
	// DeleteMapping ta usando um path dinmico
	// PathVariable: diz que o id eh o mesmo usado no path dinamico
	public void excluir(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@DeleteMapping("inativar/{id}")
	@Transactional
	public void inativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.inativar();
	}

	@PutMapping("ativar/{id}")
	@Transactional
	public void ativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.ativar();
	}
}
