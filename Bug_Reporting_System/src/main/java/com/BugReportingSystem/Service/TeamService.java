package com.BugReportingSystem.Service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.BugReportingSystem.Entity.Team;

@Component
public interface TeamService {

	List<Team> getAllTeams();

	Team saveTeam(Team team);
	
	Team getTeamByName(String name);

	Team updateTeam(Team team);

	Team getTeamById(int id);

	void deleteTeamById(int id);
	
	int teamCount();
}
