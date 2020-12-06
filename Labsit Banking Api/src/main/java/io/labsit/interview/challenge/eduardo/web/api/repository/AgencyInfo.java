package io.labsit.interview.challenge.eduardo.web.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyInfo extends
		CrudRepository<io.labsit.interview.challenge.eduardo.web.api.repository.model.account.AgencyInfo, Integer> {

}
