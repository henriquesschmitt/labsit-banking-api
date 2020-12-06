package io.labsit.interview.challenge.eduardo.web.api.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.account.AccountId;
import io.labsit.interview.challenge.eduardo.web.api.repository.model.account.ClientAccount;


@Repository
public interface ClientAccountRepository extends CrudRepository<ClientAccount, AccountId>{
	@Modifying
	@Query("UPDATE ClientAccount ca SET ca.balance = :newBalance WHERE ca.agencyNumber = :ag AND ca.accountNumber = :ac")
	public int updateBalance(@Param("newBalance")BigDecimal newBalance, @Param("ag")Integer agencyNumber, @Param("ac")Long accountNumber);
	
	public ClientAccount findByCpfCnpj(@Param("cpfCnpj")String cpfCnpj);
}
