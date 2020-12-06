package io.labsit.interview.challenge.eduardo.web.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.user.UserRole;

@Repository
public interface UserStatusTypeRepository extends CrudRepository<UserRole, Integer> {
}
