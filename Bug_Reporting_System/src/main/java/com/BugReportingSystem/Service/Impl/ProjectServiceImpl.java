package com.BugReportingSystem.Service.Impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BugReportingSystem.Entity.Project;
import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;
import com.BugReportingSystem.Repository.ProjectRepository;
import com.BugReportingSystem.Repository.TeamRepository;
import com.BugReportingSystem.Service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired 
	ProjectRepository projectRepo;
	
	@Autowired
	TeamRepository teamRepository;

	@Override
	public List<Project> getAllProject() {
		// TODO Auto-generated method stub
		return projectRepo.findAll();
	}

	@Override
	public Project saveProject(Project project) {
		// TODO Auto-generated method stub
		return projectRepo.save(project);
	}

	@Override
	public Project getProjectByName(Project project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project updateProject(Project project) {
		// TODO Auto-generated method stub
		return projectRepo.save(project);
	}

	@Override
	public Project getProjectById(int id) {
		// TODO Auto-generated method stub
		return projectRepo.findById(id).get();
	}

	@Override
	public void deleteProjectById(int id) {
		projectRepo.deleteById(id);
		
	}

	@Override
	public int ProjectCount() {
		// TODO Auto-generated method stub
		return (int) projectRepo.count();
	}

	@Override
	public List<Project> findAllByTeam(Team team) {
		// TODO Auto-generated method stub
		return projectRepo.findAllByTeam(team);
	}

	@Override
	public List<Project> getAllByStatus(int staus) {
		
		return projectRepo.findAllByStatus(staus);
	}

	@Override
	public Set<Project> findAllByUser(User user) {
		if(Objects.nonNull(user))
		{
			List<Team> teams=teamRepository.findAllByUser(user);
			Set<Project> projects=new HashSet<Project>();
			for (int i = 0; i < teams.size(); i++) {
				Team team=teams.get(i);
				projects.addAll(projectRepo.findAllByTeam(team));
				
			} 
			return projects;
			
 		}
		return null;
	}

	@Override
	public int projectCountByUser(User user) {
		if(Objects.nonNull(user))
		{
			List<Team> teams=teamRepository.findAllByUser(user);
			Set<Project> projects=new HashSet<Project>();
			for (int i = 0; i < teams.size(); i++) {
				Team team=teams.get(i);
				projects.addAll(projectRepo.findAllByTeam(team));
				
			} 
			return projects.size();
		}
		return 0;
	}

	
	
	

}
