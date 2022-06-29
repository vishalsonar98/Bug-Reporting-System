package com.BugReportingSystem.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BugReportingSystem.Entity.Bug;
import com.BugReportingSystem.Entity.Project;
import com.BugReportingSystem.Entity.User;
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

	@Override
	public List<Bug> getBugByTester(User user,String status) {
		List<Bug> exbugs=bugRepository.findAllByTesterId(user);
		List <Bug> bugs=new ArrayList<Bug>();
		
		switch (status) {
		case "Developer": {
			
			for (int i = 0; i < exbugs.size(); i++) {
				Bug bug=exbugs.get(i);
				if (bug.getBugStatus().contains(status)||bug.getBugStatus().contains("Tester")) {
					bugs.add(bug);
				}
			}
			break;
		}
		case "Fixed":{
			for (int i = 0; i < exbugs.size(); i++) {
				Bug bug=exbugs.get(i);
				if (bug.getBugStatus().contains("Fixed")) {
					bugs.add(bug);
				}
			}
			break;
		}
		}
		
			
		return bugs;
	}

	@Override
	public List<Bug> findAllByUser(User user) {
		
		return bugRepository.findAllByDevloperId(user);
	}

	@Override
	public List<Bug> getDeveloperStatusBug(List<Bug> bug) {
		if (Objects.nonNull(bug)) {
			List<Bug> bugs=new ArrayList<Bug>();
			for (int i = 0; i < bug.size(); i++) {
				Bug bug1=bug.get(i);
				if(bug1.getBugStatus().contains("Developer"))
				{
					bugs.add(bug1);
				}
			}
			return bugs;
		}
		return null;
	}

}
