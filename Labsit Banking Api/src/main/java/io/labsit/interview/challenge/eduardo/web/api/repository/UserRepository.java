package io.labsit.interview.challenge.eduardo.web.api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.user.User;


@Repository
public interface UserRepository extends CrudRepository<User, String>{
	
	@Modifying
	@Query("UPDATE User u SET u.statusTypeId = 3 WHERE u.userId = :userId")
	public int deleteUser(@Param("userId") String userId);
	
	@Query("FROM User u WHERE u.userId = :userId AND u.statusTypeId = 1")
	public User findActiveById(@Param("userId") String userId);
}
