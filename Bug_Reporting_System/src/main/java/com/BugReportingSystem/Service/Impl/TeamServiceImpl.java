package com.BugReportingSystem.Service.Impl;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.BugReportingSystem.Entity.Project;
import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;
import com.BugReportingSystem.Repository.TeamRepository;
import com.BugReportingSystem.Repository.UserRepository;
import com.BugReportingSystem.Service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamRepository teamRepo;

	@Autowired
	UserRepository userRepository;

	Model model;

	@Override
	public List<Team> getAllTeams() {

		return teamRepo.findAll();
	}

	@Override
	public Team saveTeam(Team team) {

		return teamRepo.save(team);
	}

	@Override
	public Team getTeamByName(String name) {

		return null;
	}

	@Override
	public Team updateTeam(Team team) {

		return teamRepo.save(team);
	}

	@Override
	public Team getTeamById(int id) {

		return teamRepo.findById(id).get();
	}

	@Override
	public void deleteTeamById(int id) {
		teamRepo.deleteById(id);

	}

	@Override
	public int teamCount() {

		return (int) teamRepo.count();
	}

	@Override
	public List<Team> findAllByUser(User user) {

		return teamRepo.findAllByUser(user);
	}

	@Override
	public List<Team> findAllByProject(Project project) {

		return teamRepo.findAllByProject(project);
	}

	@Override
	public List<Team> viewTeamsByUser(Principal p) {

		User user = userRepository.getUserByUserName(p.getName());

		if (Objects.nonNull(user)) {

			return teamRepo.findAllByUser(user);
		}
		return null;
	}

	@Override
	public int teamsCountByUser(User user) {
		List<Team> teams=teamRepo.findAllByUser(user);
		
		return teams.size();
	}

}
