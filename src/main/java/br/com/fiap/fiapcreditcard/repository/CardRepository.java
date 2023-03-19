package br.com.fiap.fiapcreditcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.fiapcreditcard.entity.Student;
import br.com.fiap.fiapcreditcard.entity.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {

	public List<Card> findAllByStudent(Student student);
}
