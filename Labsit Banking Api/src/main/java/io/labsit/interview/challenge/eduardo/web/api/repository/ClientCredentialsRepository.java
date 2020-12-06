package io.labsit.interview.challenge.eduardo.web.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.security.Credential;

@Repository
public interface ClientCredentialsRepository extends CrudRepository<Credential, String>{
	
}
