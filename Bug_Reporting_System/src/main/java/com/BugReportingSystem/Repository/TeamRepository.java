package com.BugReportingSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;

public interface TeamRepository extends JpaRepository<Team, Integer>
{
	
	List<Team> findAllByUser(User user);
}
