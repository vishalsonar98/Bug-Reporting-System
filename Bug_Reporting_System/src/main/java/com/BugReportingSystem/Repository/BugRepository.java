package com.BugReportingSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BugReportingSystem.Entity.Bug;
import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;

public interface BugRepository extends JpaRepository<Bug, Integer>{
	List<Bug> findAllByTesterId(User user);
	List<Bug> findAllByDevloperId(User user);
}
