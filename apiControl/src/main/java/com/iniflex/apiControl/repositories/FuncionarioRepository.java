package com.iniflex.apiControl.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iniflex.apiControl.models.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	List<Funcionario> findAllByOrderBySalario();

	List<Funcionario> findAllByOrderByPessoaNome();

	@Query("SELECT SUM(f.salario) FROM Funcionario f")
	BigDecimal sumSalario();

	List<Funcionario> findByOrderByPessoaDataNascimentoAsc();

	@Query(value = "SELECT f.funcao, STRING_AGG(p.nome, ', ')  FROM pessoas p INNER JOIN funcionarios f ON p.pessoa_id = f.pessoa_id GROUP BY f.funcao", nativeQuery = true)
	List<Object[]> findFuncionariosAgrupadosPorFuncao();

	@Query(value = "SELECT p.nome, f.funcao, f.salario, p.data_nascimento   FROM pessoas p INNER JOIN funcionarios f ON p.pessoa_id = f.pessoa_id", nativeQuery = true)
	List<Object[]> listaTodosFuncionarios();

	Optional<Funcionario> findByPessoaNome(String nome);

	void deleteByPessoaNome(String nome);
	
	  @Query(value ="SELECT  p.nome, f.salario / 1212.00  FROM pessoas p INNER JOIN funcionarios f ON p.pessoa_id = f.pessoa_id", nativeQuery = true)
	   List<Object[]> findSalariosMinimos();

}
