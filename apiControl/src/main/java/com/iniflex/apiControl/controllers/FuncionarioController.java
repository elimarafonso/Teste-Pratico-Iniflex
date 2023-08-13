package com.iniflex.apiControl.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iniflex.apiControl.DTO.FuncionarioDTO;
import com.iniflex.apiControl.models.Funcionario;
import com.iniflex.apiControl.services.FuncionarioService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/funcionario")
public class FuncionarioController {

	final FuncionarioService funcionarioService;

	public FuncionarioController(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	// ENDPOINT 3.1 – Inserir todos os funcionários, na mesma ordem e informações da
	// tabela acima.
	@PostMapping("/")
	public ResponseEntity<Object> saveFuncionario(@RequestBody @Valid FuncionarioDTO funcionarioDTO) {

		var funcionario = new Funcionario();
		funcionario = funcionario.CriaFuncionario(funcionarioDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.save(funcionario));
	}

	/*
	 * endpoint 3.2 – Remover o funcionário “João” da lista. ESTE ENDPOINT EXCLUI
	 * QUALQUER FUNCIONARIO PELO NOME
	 */
	@DeleteMapping("/deletafuncionario/{nome}")
	public ResponseEntity<Object> deletaCategoria(@PathVariable String nome) {
		return funcionarioService.deletafuncionario(nome);
	}

	/*
	 * endpoint 3.3 – Imprimir todos os funcionários com todas suas informações,
	 * sendo que: • informação de data deve ser exibido no formato dd/mm/aaaa; •
	 * informação de valor numérico deve ser exibida no formatado com separador de
	 * milhar como ponto e decimal como vírgula.
	 */
	@GetMapping("/listaFuncionarios")
	public ResponseEntity<List<Map<String, Object>>> listaFuncionarios() {
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.listaFuncionarios());
	}

	// endpoint 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar
	// a lista de funcionários com novo valor.
	@GetMapping("/atualizaSalario")
	public ResponseEntity<List<Funcionario>> aumentaSalario() {
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.aumentaSalario());
	}

	// endpoint 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a
	// “função” e o valor a “lista de funcionários”.
	@GetMapping("/listaFuncao")
	public ResponseEntity<Map<String, List<String>>> findFuncionarios() {

		List<Funcionario> funcionarios = new ArrayList<>();

		funcionarios = funcionarioService.findAll();

		Map<String, List<String>> funcionariosPorFuncao = funcionarios.stream().collect(Collectors
				.groupingBy(Funcionario::getFuncao, Collectors.mapping(Funcionario::getNome, Collectors.toList())));

		return ResponseEntity.status(HttpStatus.OK).body(funcionariosPorFuncao);
	}

	// endpoint 3.6 – Imprimir os funcionários, agrupados por função.
	@GetMapping("/funcionariosFuncao")
	public ResponseEntity<List<Object[]>> findFuncionariosByFuncao() {

		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.agrupaFuncionarios());

	}

	// NÃO EXISTE UM ITEM 3.7

	// endpoint 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
	@GetMapping("/listaAniversariantes")
	public ResponseEntity<List<Object>> findAniversariantes() {
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.listarAniversariantes());
	}

	// endpoint 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos:
	// nome e id
	@GetMapping("/maiorIdade")
	public ResponseEntity<String> findFuncionario() {

		Map<String, String> funcionario;

		funcionario = funcionarioService.findFuncionario();

		String pessoa = String.format("Nome: " + funcionario.get("nome") + " Idade: " + funcionario.get("idade"));

		return ResponseEntity.status(HttpStatus.OK).body(pessoa);
	}

	// endpoint 3.10 – Imprimir a lista de funcionários por ordem alfabética.
	@GetMapping("/listagemOrdem")
	public ResponseEntity<List<Funcionario>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.listarFuncionariosPorNome());

	}

	// endpoint 3.11 – Imprimir o total dos salários dos funcionários.
	@GetMapping("/totalSalario")
	public ResponseEntity<String> totalSalario() {

		BigDecimal totalSalario = funcionarioService.totalSalario();
		String mensagem = String.format("Total Salários: R$ %.2f", totalSalario);
		return ResponseEntity.status(HttpStatus.OK).body(mensagem);
	}

	// endpoint 3.12 – Imprimir quantos salários mínimos ganha cada funcionário,
	// considerando que o salário mínimo é R$1212.00.
	@GetMapping("/salarioMinimo")
	public ResponseEntity<List<Object[]>> salarioMinimo() {

		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.salarioMinimo());
	}

}
