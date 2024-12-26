package com.example.Curso.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Curso.infra.DadosTokenJWT;
import com.example.Curso.infra.TokenService;
import com.example.Curso.usuarios.DadosAutenticacao;
import com.example.Curso.usuarios.Usuario;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var autenticacao = manager.authenticate(token);
		System.out.println("senha digitada: " + dados.senha());

		var tokenJWT = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());

		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));

		// return ResponseEntity.ok(tokenService.gerarToken((Usuario)
		// autenticacao.getPrincipal()));

	}
}
