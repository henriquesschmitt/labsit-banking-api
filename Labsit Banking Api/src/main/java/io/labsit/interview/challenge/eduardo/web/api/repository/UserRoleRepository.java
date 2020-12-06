package io.labsit.interview.challenge.eduardo.web.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.user.StatusType;

@Repository
public interface UserRoleRepository extends CrudRepository<StatusType, Integer> {
}
