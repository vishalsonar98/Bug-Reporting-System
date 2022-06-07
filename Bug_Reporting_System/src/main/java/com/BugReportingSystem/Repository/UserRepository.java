package com.BugReportingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BugReportingSystem.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
}
