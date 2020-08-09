package com.neeraj.mybank.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.neeraj.mybank.service.TransactionService;

public class Application {

	public static TransactionService transactionService= new TransactionService();
	
	// You need to explicitly register the JavaTimeModule
	public static ObjectMapper objectMapper= new ObjectMapper().registerModule(new JavaTimeModule());;
}
