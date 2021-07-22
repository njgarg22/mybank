package com.neeraj.mybank.service;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.neeraj.mybank.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionService {
	private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

	private final String bankSlogan;
	private final JdbcTemplate jdbcTemplate;

	public TransactionService(@Value("${bank.slogan}") String bankSlogan, JdbcTemplate jdbcTemplate) {
		this.bankSlogan = bankSlogan;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public List<Transaction> findAll(){
		return jdbcTemplate.query("select id, amount, timestamp, reference, bank_slogan, receiving_user_id from transactions", TransactionService::mapRow);
	}

	@Transactional
	public List<Transaction> findAllByReceivingUserId(String receivingUserId){
		return jdbcTemplate.query(con -> {
			PreparedStatement statement = con.prepareStatement("SELECT tx.id, tx.amount, tx.timestamp, tx.reference, tx.bank_slogan, tx.receiving_user_id FROM TRANSACTIONS tx WHERE tx.receiving_user_id = ?");
			statement.setString(1, receivingUserId);
			return statement;
		}, TransactionService::mapRow);
	}

	@Transactional
	public Transaction create(BigDecimal amount, String reference, String receivingUserId) {
		ZonedDateTime timestamp = LocalDateTime.now().atZone(DEFAULT_ZONE);
		Transaction transaction = new Transaction(amount, timestamp, reference, bankSlogan, receivingUserId);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO TRANSACTIONS (amount, timestamp, reference, bank_slogan, receiving_user_id) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setBigDecimal(1, transaction.getAmount());
			ps.setTimestamp(2, Timestamp.valueOf(transaction.getTimestamp().toLocalDateTime()));
			ps.setString(3, transaction.getReference());
			ps.setString(4, transaction.getBankSlogan());
			ps.setString(5, transaction.getReceivingUserId());
			return ps;
		}, keyHolder);

		String uuid = !keyHolder.getKeys().isEmpty() ? keyHolder.getKeys().values().iterator().next().toString() : null;
		transaction.setId(uuid);
		return transaction;
	}

	private static Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
		Transaction tx = new Transaction();
		tx.setId(rs.getObject("id").toString());
		tx.setAmount(rs.getBigDecimal("amount"));
		tx.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime().atZone(DEFAULT_ZONE));
		tx.setReference(rs.getString("reference"));
		tx.setBankSlogan(rs.getString("bank_slogan"));
		tx.setReceivingUserId(rs.getString("receiving_user_id"));
		return tx;
	}
}
