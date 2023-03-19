package br.com.fiap.fiapcreditcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.fiapcreditcard.entity.Card;
import br.com.fiap.fiapcreditcard.entity.Purchase;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

	public List<Purchase> findByCard(Card card);
}
