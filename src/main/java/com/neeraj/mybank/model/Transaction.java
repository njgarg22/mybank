package com.neeraj.mybank.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Transaction {
	private String id;
	private BigDecimal amount;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmZ")
	private ZonedDateTime timestamp;
	private String reference;
	private String bankSlogan;

	public Transaction() {
		
	}
	
	public Transaction(BigDecimal amount, ZonedDateTime timestamp, String reference, String bankSlogan) {
		this.id = UUID.randomUUID().toString();
		this.amount = amount;
		this.timestamp = timestamp;
		this.reference = reference;
		this.bankSlogan = bankSlogan;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getBankSlogan() {
		return bankSlogan;
	}

	public void setBankSlogan(String bankSlogan) {
		this.bankSlogan = bankSlogan;
	}
}
