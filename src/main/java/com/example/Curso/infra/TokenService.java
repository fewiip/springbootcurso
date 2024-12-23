package com.example.Curso.infra;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.Curso.usuarios.Usuario;

@Service
public class TokenService {
	/*
	 * public String gerarToken(Usuario usuario) {
	 * try {
	 * Algorithm algorithm = Algorithm.RSA256(RSAPublicKey, RSAPrivateKey);
	 * String token = JWT.create().withIssuer("auth0").sign(algorithm);
	 * } catch (JWTCreationException exception) {
	 * }
	 * }
	 */

	public String gerarToken(Usuario usuario) {
		try {
			System.out.println("senha: " + usuario.getSenha());
			var algorithm = Algorithm.HMAC256("123456");
			return JWT.create().withIssuer("Remedios_api").withSubject(usuario.getLogin())
					.withExpiresAt(dataExpiracao()).sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Erro ao gerar token", exception);
		}

	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
