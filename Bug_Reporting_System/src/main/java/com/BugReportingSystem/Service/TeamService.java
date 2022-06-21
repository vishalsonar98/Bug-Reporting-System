package com.BugReportingSystem.Service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.BugReportingSystem.Entity.Project;
import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;

@Component
public interface TeamService {

	List<Team> getAllTeams();

	Team saveTeam(Team team);
	
	Team getTeamByName(String name);

	Team updateTeam(Team team);

	Team getTeamById(int id);

	void deleteTeamById(int id);
	
	int teamCount();
	
	List<Team> findAllByUser(User user);
	List<Team> findAllByProject(Project project);
}
