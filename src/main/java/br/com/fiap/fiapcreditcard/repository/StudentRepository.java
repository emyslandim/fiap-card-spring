package br.com.fiap.fiapcreditcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.fiapcreditcard.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	List<Student> findAllByHaveCard(Boolean haveCard);
}