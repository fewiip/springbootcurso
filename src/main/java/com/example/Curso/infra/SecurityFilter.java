package com.example.Curso.infra;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Curso.usuarios.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
		// pegar o subject e validado ou nao
		// if filter

		var tokenJWT = recuperarToken(request);
		if (tokenJWT != null) {
			var subject = tokenService.getSubject(tokenJWT);
			var usuario = repository.findByLogin(subject);
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
					usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		// se ele passar dessas duas linhas eh porque o token ja foi validado

		// System.out.println("felipinho: " + tokenJWT);
		filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");

		/*
		 * if (authorizationHeader == null) {
		 * throw new RuntimeException("token nao autorizado");
		 * 
		 */
		if (authorizationHeader != null) {
			System.out.println("felipinho header:");
			return authorizationHeader;
		}

		System.out.println("sem felipinho header");
		// return authorizationHeader;

		return null;
	}
}
