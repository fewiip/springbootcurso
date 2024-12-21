package com.example.Curso.infra;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Service
public class TokenService {
	public String gerarToken() {
		try {
			Algorithm algorithm = Algorithm.RSA256(RSAPublicKey, RSAPrivateKey);
			String token = JWT.create().withIssuer("auth0").sign(algorithm);
		} catch (JWTCreationException exception) {
		}
	}
}
