package io.labsit.interview.challenge.eduardo.web.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.client.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, String>{
	@Query(value = "SELECT * FROM CLIENT_SCHEMA.CLIENT C INNER JOIN (SELECT * FROM USER_SCHEMA.USER U WHERE NOT U.STATUS_TYPE_ID = 3) U ON (C.USER_ID = U.USER_ID)", nativeQuery = true)
	public List<Client> getAllActive();
	
	@Query(value = "(SELECT * FROM CLIENT_SCHEMA.CLIENT C INNER JOIN (SELECT * FROM USER_SCHEMA.USER U WHERE NOT U.STATUS_TYPE_ID = 3) U ON (C.USER_ID = U.USER_ID) WHERE C.ID = :cpfCnpj)", nativeQuery = true)
	public Client findActiveById(@Param(value = "cpfCnpj") String cpfCnpj);
}
