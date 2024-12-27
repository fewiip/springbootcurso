package com.example.Curso.infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
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

	@Value("${api.security.token.secret}")
	private String secret;

	public String gerarToken(Usuario usuario) {
		try {
			// System.out.println("senha: " + usuario.getSenha());

			var algorithm = Algorithm.HMAC256(secret);
			return JWT.create().withIssuer("API Remedios")
					.withSubject(usuario.getLogin())
					.withExpiresAt(dataExpiracao())
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Erro ao gerar token", exception);
		}

	}

	// faz a validacao
	public String getSubject(String tokenJWT) {
		// return null;
		try {
			// JWTVerifier verifier = JWT.require(algorithm)
			Algorithm algorithm = Algorithm.HMAC256(secret);

			return JWT.require(algorithm).withIssuer("API Remedios").build().verify(tokenJWT).getSubject();

		} catch (JWTVerificationException exception) {
			// Invalid signature/claims
			throw new RuntimeException("Token Invalido ou expirado");
		}
	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
