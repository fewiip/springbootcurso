package com.example.Curso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursoApplication {
	public String teste = "hello world";

	// public static String = "teste";

	public static void main(String[] args) {
		SpringApplication.run(CursoApplication.class, args);
	}

}
