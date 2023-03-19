package br.com.fiap.fiapcreditcard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.fiapcreditcard.entity.Student;
import br.com.fiap.fiapcreditcard.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@PostMapping
	public Student create(@RequestBody Student student) {
		return studentService.create(student);
	}

	@DeleteMapping("/{id}")
	public void delete(@RequestParam Integer id) {
		studentService.delete(id);
	}

	@GetMapping
	public List<Student> findAll(@RequestParam(required = false) Boolean haveCard) {
		if (haveCard == null) {
			return studentService.findAll();
		}

		return studentService.listByCard(haveCard);
	}
}
