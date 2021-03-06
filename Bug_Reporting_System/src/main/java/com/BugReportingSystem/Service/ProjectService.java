package com.BugReportingSystem.Service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.BugReportingSystem.Entity.Project;
import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;



@Component
public interface ProjectService {
	List<Project> getAllProject();

	List<Project> getAllByStatus(int staus);
	
	Project saveProject(Project project);
	
	Project getProjectByName(Project project);

	Project updateProject(Project project);

	Project getProjectById(int id);

	void deleteProjectById(int id);
	
	int ProjectCount();
	
	List<Project> findAllByTeam(Team team);
	
	Set<Project> findAllByUser(User user);

	int projectCountByUser(User user);
}
