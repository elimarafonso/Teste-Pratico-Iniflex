package com.iniflex.apiControl.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import com.iniflex.apiControl.DTO.FuncionarioDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "funcionarios")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long funcionario_id;

	@Column(name = "salario", nullable = false, length = 10)
	private BigDecimal salario;

	@Column(name = "funcao", nullable = false, length = 255)
	private String funcao;

	@OneToOne
	@JoinColumn(name = "pessoa_id", referencedColumnName = "pessoa_id")
	private Pessoa pessoa;

	public Funcionario CriaFuncionario(@Valid FuncionarioDTO funcionarioDTO) {

		var pessoa = new Pessoa(funcionarioDTO.getNome(), funcionarioDTO.getDataNascimento());
		var funcionario = new Funcionario(funcionarioDTO.getSalario(), funcionarioDTO.getFuncao(), pessoa);

		return funcionario;
	}

	public Funcionario(BigDecimal salario, String funcao, Pessoa pessoa) {

		this.salario = salario;
		this.funcao = funcao;
		this.pessoa = pessoa;
	}

	public int idade() {

		LocalDate anoNascimento = this.getPessoa().getDataNascimento();
		LocalDate dataAtual = LocalDate.now();
		Period idade = Period.between(anoNascimento, dataAtual);
		int anos = idade.getYears();
		return anos;
	}

	public void aumentasalario(BigDecimal valor) {

		BigDecimal salarioAtual = this.getSalario();
		BigDecimal aumento = salarioAtual.multiply(valor);
		BigDecimal novoSalario = salarioAtual.add(aumento);

		this.setSalario(novoSalario);
	}

	public String getNome() {
		return pessoa.getNome();
	}

}