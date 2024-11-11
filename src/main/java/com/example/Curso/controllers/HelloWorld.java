package com.example.Curso.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorld {
	String hello = "doasknmdsoakdmoasdmas";

	@GetMapping
	public String olaMundo() {
		return hello;
	}

}
