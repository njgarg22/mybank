package com.neeraj.mybank.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.neeraj.mybank.model.Transaction;

public class TransactionService {
	private List<Transaction> transactions = new CopyOnWriteArrayList<>();
	
	public List<Transaction> findAll(){
		return transactions;
	}
	
	public Transaction create(BigDecimal amount, String reference) {
		ZonedDateTime timestamp = ZonedDateTime.now();
		Transaction transaction = new Transaction(amount, timestamp, reference);
		transactions.add(transaction);
		return transaction;
	}
}
