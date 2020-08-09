package com.neeraj.mybank.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neeraj.mybank.context.Application;
import com.neeraj.mybank.model.Transaction;

public class TransactionServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getRequestURI().equalsIgnoreCase("/transactions")) {
			resp.setContentType("application/json;charset=UTF-8");
			List<Transaction> transactions = Application.transactionService.findAll();
			String json = Application.objectMapper.writeValueAsString(transactions);
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
			
			Transaction transaction = Application.transactionService.create(amount, reference);
			
			String json = Application.objectMapper.writeValueAsString(transaction);
			resp.setContentType("application/json;charset=UTF-8");
			resp.getWriter().print(json);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
