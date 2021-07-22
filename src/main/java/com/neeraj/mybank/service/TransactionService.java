package com.neeraj.mybank.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.neeraj.mybank.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionService {
	private final String bankSlogan;

	private List<Transaction> transactions = new CopyOnWriteArrayList<>();

	public TransactionService(@Value("${bank.slogan}") String bankSlogan) {
		this.bankSlogan = bankSlogan;
	}

	public List<Transaction> findAll(){
		return transactions;
	}

	public List<Transaction> findAllByReceivingUserId(String receivingUserId){
		return transactions.stream()
				.filter(tx -> tx.getReceivingUserId().equalsIgnoreCase(receivingUserId))
				.collect(Collectors.toList());
	}

	public Transaction create(BigDecimal amount, String reference, String receivingUserId) {
		ZonedDateTime timestamp = ZonedDateTime.now();
		Transaction transaction = new Transaction(amount, timestamp, reference, bankSlogan, receivingUserId);
		transactions.add(transaction);
		return transaction;
	}
}
