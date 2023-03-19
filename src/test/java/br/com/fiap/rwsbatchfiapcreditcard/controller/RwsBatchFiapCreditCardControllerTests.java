package br.com.fiap.fiapcreditcard.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.fiap.fiapcreditcard.entity.Card;
import br.com.fiap.fiapcreditcard.entity.Purchase;
import br.com.fiap.fiapcreditcard.entity.Student;
import br.com.fiap.fiapcreditcard.service.CardService;
import br.com.fiap.fiapcreditcard.service.PurchaseService;
import br.com.fiap.fiapcreditcard.service.StudentService;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FiapCreditCardControllerTests {

	StudentController studentController = new StudentController();
	CardController cardController = new CardController();
	PurchaseController purchaseController = new PurchaseController();

	@BeforeAll
	public void init(@Mock StudentService studentService, @Mock CardService cardService,
			@Mock PurchaseService purchaseService) {

		ReflectionTestUtils.setField(studentController, "studentService", createStudentServiceMock(studentService));

		ReflectionTestUtils.setField(cardController, "cardService", createCartaoServiceMock(cardService));

		when(purchaseService.listByCard(anyInt())).thenReturn(new ArrayList<Purchase>());
		ReflectionTestUtils.setField(cardController, "purchaseService", purchaseService);
	}

	public StudentService createStudentServiceMock(StudentService studentService) {
		Student student = new Student();
		student.setRm("168791");
		List<Student> students = new ArrayList<>();
		students.add(student);
		student.setHaveCard(true);
		students.add(student);
		List<Student> studentsCartao = new ArrayList<>();
		studentsCartao.add(student);
		when(studentService.create(any())).thenReturn(student);
		when(studentService.get(any())).thenReturn(student);
		when(studentService.findAll()).thenReturn(students);
		when(studentService.listByCard(true)).thenReturn(studentsCartao);
		when(studentService.listByCard(false)).thenReturn(new ArrayList<>());

		return studentService;
	}

	public CardService createCartaoServiceMock(CardService cardService) {
		Card card = new Card();
		card.setId(168791);
		List<Card> cards = new ArrayList<>();
		cards.add(card);
		when(cardService.create(anyInt(), any())).thenReturn(card);
		when(cardService.getOne(anyInt())).thenReturn(card);
		when(cardService.findAllByStudent(anyInt())).thenReturn(cards);

		return cardService;
	}

	@BeforeAll
	void init(@Mock PurchaseService purchaseService) {
		Purchase purchase = new Purchase();
		purchase.setDescription("Purchase Tests");
		purchase.setValue(12.22);
		purchase.setData(new Date());
		List<Purchase> purchases = new ArrayList<>();
		purchases.add(purchase);
		when(purchaseService.create(anyInt(), any())).thenReturn(purchase);
		when(purchaseService.listByCard(anyInt())).thenReturn(purchases);
		ReflectionTestUtils.setField(purchaseController, "purchaseService", purchaseService);
	}

	@Test
	public void createStudent() {
		assertEquals("168791", studentController.create(new Student()).getRm());
	}

	@Test
	public void findAllStudent() {
		assertEquals(2, studentController.findAll(null).size());
		assertEquals(1, studentController.findAll(true).size());
		assertEquals(0, studentController.findAll(false).size());
	}

	@Test
	public void createCard() {
		assertEquals(168791, cardController.create(168791, new Card()).getId());
	}

	@Test
	public void findAllByStudent() {
		assertEquals(1, cardController.findAllByStudent(168791).size());
	}

	@Test
	public void getExtract() throws IOException {
		cardController.getExtract(168791, new MockHttpServletResponse());
	}

	@Test
	public void createPurchase() {
		assertEquals(12.22, purchaseController.create(168791, new Purchase()).getValue());
	}

	@Test
	public void listByCard() {
		assertEquals(1, purchaseController.listByCard(168791).size());
	}
}
