package com.example.Curso.remedio;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

@Table(name = "remedios")
@Entity(name = "remedio")
@Getter
// @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Remedio {
	//

	public Remedio(DadosCadastroRemedio dados) {
		this.ativo = true;
		this.nome = dados.nome();
		this.via = dados.via();
		this.lote = dados.lote();
		this.quantidade = dados.quantidade();
		this.validade = dados.validade();
		this.laboratorio = dados.laboratorio();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;

	@Enumerated(EnumType.STRING)
	private Via via;
	private String lote;
	private int quantidade;
	private LocalDate validade;
	private Boolean ativo;

	@Enumerated(EnumType.STRING)
	private Laboratorio laboratorio;

	public void atualizarInformacoes(@Valid DadosAtualizarRemedio dados) {
		if (dados.nome() != null) {
			this.nome = dados.nome();
		}

		if (dados.via() != null) {
			this.via = dados.via();
		}

		if (dados.laboratorio() != null) {
			this.laboratorio = dados.laboratorio();
		}

	}

	public void inativar() {
		this.ativo = false;
	}

	public void ativar() {
		this.ativo = true;
	}

	public Long getId() {
		return this.id;
	}

	public String getNome() {
		return this.nome;
	}

	public Via getVia() {
		return this.via;
	}
}
