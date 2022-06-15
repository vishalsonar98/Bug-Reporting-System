package com.BugReportingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BugReportingSystem.Entity.Team;

public interface TeamRepository extends JpaRepository<Team, Integer>
{
	

}
