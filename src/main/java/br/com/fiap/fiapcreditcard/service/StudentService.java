package br.com.fiap.fiapcreditcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.fiapcreditcard.entity.Student;
import br.com.fiap.fiapcreditcard.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	public Student create(Student student) {
		return studentRepository.save(student);
	}

	public void delete(Integer id) {
		Student student = new Student();
		student.setId(id);
		studentRepository.delete(student);
	}

	public Student update(Integer id, Student newStudent) {
		return studentRepository.save(newStudent);
	}

	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	public List<Student> listByCard(Boolean haveCard) {
		return studentRepository.findAllByHaveCard(haveCard);
	}

	public Student get(Integer id) {
		return studentRepository.getOne(id);
	}
}