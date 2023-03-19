package br.com.fiap.fiapcreditcard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TB_CARD")
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String number;
	private String expirationDate;
	private double totalLimit;
	private double currentLimit;
	@JsonIgnore
	@Transient
	private Integer idStudent;
	@ManyToOne
	@JsonIgnore
	private Student student;
	@OneToMany(mappedBy = "card")
	@JsonIgnore
	private Set<Purchase> transactions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public double getTotalLimit() {
		return totalLimit;
	}

	public void setTotalLimit(double totalLimit) {
		this.totalLimit = totalLimit;
	}

	public double getCurrentLimit() {
		return currentLimit;
	}

	public void setCurrentLimit(double currentLimit) {
		this.currentLimit = currentLimit;
	}

	public Integer getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Integer idStudent) {
		this.idStudent = idStudent;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Set<Purchase> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Purchase> transactions) {
		this.transactions = transactions;
	}
}