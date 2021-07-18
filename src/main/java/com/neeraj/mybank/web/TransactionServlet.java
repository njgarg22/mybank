package com.neeraj.mybank.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neeraj.mybank.context.BankingConfiguration;
import com.neeraj.mybank.model.Transaction;
import com.neeraj.mybank.service.TransactionService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// Redundant due to TransactionController
public class TransactionServlet extends HttpServlet{

	private ObjectMapper objectMapper;

	private TransactionService transactionService;

	@Override
	public void init() throws ServletException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BankingConfiguration.class);
		objectMapper = ctx.getBean(ObjectMapper.class);
		transactionService = ctx.getBean(TransactionService.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getRequestURI().equalsIgnoreCase("/transactions")) {
			resp.setContentType("application/json;charset=UTF-8");
			List<Transaction> transactions = transactionService.findAll();
			String json = objectMapper.writeValueAsString(transactions);
			resp.getWriter().print(json);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);		
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getRequestURI().equalsIgnoreCase("/transactions")) {
			BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(req.getParameter("amount")));
			String reference = req.getParameter("reference");
			
			Transaction transaction = transactionService.create(amount, reference);
			
			String json = objectMapper.writeValueAsString(transaction);
			resp.setContentType("application/json;charset=UTF-8");
			resp.getWriter().print(json);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
