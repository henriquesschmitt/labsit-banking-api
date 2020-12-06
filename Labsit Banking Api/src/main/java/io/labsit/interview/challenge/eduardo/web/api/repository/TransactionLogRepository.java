package io.labsit.interview.challenge.eduardo.web.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.security.Transaction;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.security.TransactionLog;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.user.User;

@Repository
public interface TransactionLogRepository extends CrudRepository<TransactionLog, Integer> {
	@Query("FROM TransactionLog AS t WHERE t.userId = :userId AND t.transactionId IN :ids AND (t.transactionDate BETWEEN :init AND :finish)")
	public List<TransactionLog> findAllByTransactionId(@Param("userId")User userId, @Param("ids")List<Transaction> transactionsId, @Param("init")LocalDateTime init, @Param("finish")LocalDateTime finish);
}
