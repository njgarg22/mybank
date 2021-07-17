package com.neeraj.mybank.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
	
	public Transaction create(BigDecimal amount, String reference) {
		ZonedDateTime timestamp = ZonedDateTime.now();
		Transaction transaction = new Transaction(amount, timestamp, reference, bankSlogan);
		transactions.add(transaction);
		return transaction;
	}
}
