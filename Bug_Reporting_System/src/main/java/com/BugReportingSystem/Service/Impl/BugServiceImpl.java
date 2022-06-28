package com.BugReportingSystem.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BugReportingSystem.Entity.Bug;
import com.BugReportingSystem.Entity.Project;
import com.BugReportingSystem.Repository.BugRepository;
import com.BugReportingSystem.Service.BugService;

@Service
public class BugServiceImpl implements BugService {

	@Autowired
	private BugRepository bugRepository;
	@Override
	public List<Bug> getAllBugs() {
		
		return bugRepository.findAll();
	}

	@Override
	public Bug saveBug(Bug bug) {
		
		return bugRepository.save(bug);
	}

	@Override
	public Bug getBugById(int id) {
		
		return bugRepository.findById(id).get();
	}

	@Override
	public Bug updateBug(Bug bug) {
		
		return bugRepository.save(bug);
	}

	@Override
	public void deleteBugById(int id) {
		
		bugRepository.deleteById(id);;
	}

	@Override
	public List<Bug> findAllByProject(Project project) {
		
		return null;
	}

}
