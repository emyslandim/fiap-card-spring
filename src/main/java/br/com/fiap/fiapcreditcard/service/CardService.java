package br.com.fiap.fiapcreditcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.fiapcreditcard.entity.Student;
import br.com.fiap.fiapcreditcard.entity.Card;
import br.com.fiap.fiapcreditcard.repository.CardRepository;

import java.util.List;

@Service
public class CardService {

	@Autowired
	CardRepository cardRepository;

	@Autowired
	StudentService studentService;

	public Card create(Integer idStudent, Card card) {
		Student student = studentService.get(idStudent);
		student.setHaveCard(true);
		studentService.update(idStudent, student);
		card.setStudent(student);
		card.setCurrentLimit(card.getTotalLimit());
		return cardRepository.save(card);
	}

	public void delete(Integer idStudent, Integer id) {
		Card card = new Card();
		card.setId(id);
		cardRepository.delete(card);
		if (findAllByStudent(idStudent).size() == 0) {
			Student student = studentService.get(idStudent);
			student.setHaveCard(false);
			studentService.update(idStudent, student);
		}
	}

	public List<Card> findAllByStudent(Integer idStudent) {
		Student student = new Student();
		student.setId(idStudent);
		return cardRepository.findAllByStudent(student);
	}

	public Card getOne(Integer id) {
		return cardRepository.getOne(id);
	}
}