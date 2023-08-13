package com.iniflex.apiControl.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {

	private String nome;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataNascimento;

	private BigDecimal salario;

	private String funcao;

}
