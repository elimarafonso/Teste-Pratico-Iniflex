package com.iniflex.apiControl.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pessoas")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pessoa_id;

	@Column(name = "nome", nullable = false, length = 255)
	private String nome;

	@Column(name = "dataNascimento", nullable = false)
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataNascimento;

	public Pessoa(String nome, LocalDate dataNascimento) {

		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}

}