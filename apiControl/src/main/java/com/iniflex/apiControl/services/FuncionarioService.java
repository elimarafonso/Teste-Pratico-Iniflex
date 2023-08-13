package com.iniflex.apiControl.services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iniflex.apiControl.models.Funcionario;
import com.iniflex.apiControl.repositories.FuncionarioRepository;
import com.iniflex.apiControl.repositories.PessoaRepository;

@Service
public class FuncionarioService {

	private final PessoaRepository pessoaRepository;
	private final FuncionarioRepository funcionarioRepository;

	@Autowired
	public FuncionarioService(PessoaRepository pessoaRepository, FuncionarioRepository funcionarioRepository) {
		this.pessoaRepository = pessoaRepository;
		this.funcionarioRepository = funcionarioRepository;
	}

	@Transactional
	public Funcionario save(Funcionario funcionario) {
		pessoaRepository.save(funcionario.getPessoa());
		return funcionarioRepository.save(funcionario);
	}

	public List<Funcionario> listarFuncionariosPorNome() {
		return funcionarioRepository.findAllByOrderByPessoaNome();
	}

	public BigDecimal totalSalario() {

		return funcionarioRepository.sumSalario();
	}

	public Map<String, String> findFuncionario() {

		List<Funcionario> func = funcionarioRepository.findByOrderByPessoaDataNascimentoAsc();

		Funcionario funcionario = func.get(0);

		Map<String, String> pessoa = new HashMap<>();

		String nome = funcionario.getPessoa().getNome();
		int idade = funcionario.idade();

		pessoa.put("nome", nome);
		pessoa.put("idade", Integer.toString(idade));

		return pessoa;
	}

	public List<Object> listarAniversariantes() {

		List<Object> aniversariantes = pessoaRepository.findAllAniversariantesOutubroDezembro();

		return aniversariantes;
	}

	public List<Object[]> agrupaFuncionarios() {

		return funcionarioRepository.findFuncionariosAgrupadosPorFuncao();
	}

	public List<Funcionario> findAll() {

		return funcionarioRepository.findAll();
	}

	@Transactional
	public List<Funcionario> aumentaSalario() {

		BigDecimal porcentagem = new BigDecimal("0.1");
		List<Funcionario> todosFuncionarios = funcionarioRepository.findAll();

		for (Funcionario funcionario : todosFuncionarios) {
			funcionario.aumentasalario(porcentagem);
		}

		return todosFuncionarios;
	}

	public List<Map<String, Object>> listaFuncionarios() {

		List<Map<String, Object>> listaMapas = criarListaMapas(funcionarioRepository.listaTodosFuncionarios());

		return listaMapas;
	}

	// Converte List<Object[]> em List<Map<String, Object>>
	public static List<Map<String, Object>> criarListaMapas(List<Object[]> resultadoQuery) {
		List<Map<String, Object>> listaMapas = new ArrayList<>();

		// Configurando o DecimalFormat para o padrão brasileiro
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

		// DateTimeFormatter para formatar o valor LocalDate no formato (dd/mm/yyyy)
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		for (Object[] row : resultadoQuery) {
			Map<String, Object> mapa = new LinkedHashMap<>();
			mapa.put("Nome", row[0]);
			mapa.put("Funcao", row[1]);

			BigDecimal salario = (BigDecimal) row[2];
			mapa.put("Salario ", decimalFormat.format(salario)); // Formata o BigDecimal

			// Converte java.sql.Date para java.time.LocalDate
			java.sql.Date sqlDate = (java.sql.Date) row[3];
			LocalDate datanascimento = sqlDate.toLocalDate();
			mapa.put("Data de Nascimento", datanascimento.format(dateFormatter)); // Formata o LocalDate

			listaMapas.add(mapa);
		}

		return listaMapas;
	}

	@Transactional
	public ResponseEntity<Object> deletafuncionario(String nome) {

		Optional<Funcionario> existeFuncionario = funcionarioRepository.findByPessoaNome(nome);
		if (existeFuncionario.isPresent()) {
			funcionarioRepository.deleteByPessoaNome(nome);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	public  List<Object[]> salarioMinimo() {

		return funcionarioRepository.findSalariosMinimos();
	}

}
