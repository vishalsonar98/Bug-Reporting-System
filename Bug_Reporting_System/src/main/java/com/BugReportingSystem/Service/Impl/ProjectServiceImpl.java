package com.BugReportingSystem.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BugReportingSystem.Entity.Project;
import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Repository.ProjectRepository;
import com.BugReportingSystem.Service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired 
	ProjectRepository projectRepo;

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

	
	
	

}
