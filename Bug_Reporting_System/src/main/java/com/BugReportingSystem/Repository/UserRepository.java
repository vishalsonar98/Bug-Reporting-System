package com.BugReportingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BugReportingSystem.Entity.User;

/*
* UserRepository extends JpaRepository which gives 
* various methods to perform varios operations on user entity.
*/
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("select u from User u where u.email = :email")
	public User getUserByUserName(@Param("email") String email);
	
	
}
