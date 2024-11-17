package com.example.Curso.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.Curso.remedio.DadosAtualizarRemedio;
import com.example.Curso.remedio.DadosCadastroRemedio;
import com.example.Curso.remedio.DadosListagemRemedio;
import com.example.Curso.remedio.DadosDetalhamentoRemedio;
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
	public ResponseEntity<DadosDetalhamentoRemedio> cadastrar(@RequestBody @Valid DadosCadastroRemedio dados,
			UriComponentsBuilder uriComponentsBuilder) {
		System.out.println(dados);
		var remedio = new Remedio(dados);
		repository.save(remedio);
		var uri = uriComponentsBuilder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoRemedio(remedio));
	}

	@GetMapping
	public ResponseEntity<List<DadosListagemRemedio>> listar() {
		var lista = repository.findAllByAtivoTrue().stream().map(DadosListagemRemedio::new).toList();
		return ResponseEntity.ok(lista);

	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoRemedio> atualizar(@RequestBody @Valid DadosAtualizarRemedio dados) {
		var remedio = repository.getReferenceById(dados.id());
		// acessa o banco de dados e pega os dados pelo ID
		remedio.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}

	@DeleteMapping("/{id}")
	// DeleteMapping ta usando um path dinmico
	// PathVariable: diz que o id eh o mesmo usado no path dinamico
	public void excluir(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@DeleteMapping("inativar/{id}")
	@Transactional
	public ResponseEntity<Void> inativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.inativar();

		return ResponseEntity.noContent().build();
	}

	@PutMapping("reativar/{id}")
	@Transactional
	public ResponseEntity<Void> ativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.ativar();

		return ResponseEntity.noContent().build();
	}
}
