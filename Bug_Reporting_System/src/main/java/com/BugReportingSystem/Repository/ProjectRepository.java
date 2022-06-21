package com.BugReportingSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BugReportingSystem.Entity.Project;
import com.BugReportingSystem.Entity.Team;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	public List<Project> findAllByStatus(int status);
	public List<Project> findAllByTeam(Team team);
}
