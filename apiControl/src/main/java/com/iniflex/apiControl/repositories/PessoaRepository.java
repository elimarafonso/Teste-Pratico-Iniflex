package com.iniflex.apiControl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iniflex.apiControl.models.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	@Query("SELECT p.nome, p.dataNascimento  FROM Pessoa p  WHERE MONTH(p.dataNascimento) IN (10, 12)")
	List<Object> findAllAniversariantesOutubroDezembro();
}
