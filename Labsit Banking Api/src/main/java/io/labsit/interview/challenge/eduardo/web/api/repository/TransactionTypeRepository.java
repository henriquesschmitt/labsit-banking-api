package io.labsit.interview.challenge.eduardo.web.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.security.Transaction;

@Repository
public interface TransactionTypeRepository extends CrudRepository<Transaction, Integer> {
}
