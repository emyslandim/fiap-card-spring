package br.com.fiap.fiapcreditcard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.fiapcreditcard.entity.Card;
import br.com.fiap.fiapcreditcard.entity.Purchase;
import br.com.fiap.fiapcreditcard.repository.PurchaseRepository;

import java.util.List;

@Service
public class PurchaseService {

	@Autowired
	PurchaseRepository purchaseRepository;

	@Autowired
	CardService cardService;

	public Purchase create(Integer idCard, Purchase purchase) {
		Card card = cardService.getOne(idCard);
		card.setId(idCard);
		card.setCurrentLimit(card.getCurrentLimit() - purchase.getValue());
		purchase.setCard(card);
		return purchaseRepository.save(purchase);
	}

	public void delete(Integer idCard, Integer id) {
		Purchase purchase = getOne(id);
		Card card = cardService.getOne(idCard);
		card.setId(idCard);
		card.setCurrentLimit(card.getCurrentLimit() + purchase.getValue());
		purchaseRepository.delete(purchase);
	}

	public List<Purchase> findAll() {
		return purchaseRepository.findAll();
	}

	public List<Purchase> listByCard(Integer idCard) {
		Card card = new Card();
		card.setId(idCard);
		return purchaseRepository.findByCard(card);
	}

	public Purchase getOne(Integer id) {
		return purchaseRepository.getOne(id);
	}
}