package com.BugReportingSystem.Service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.BugReportingSystem.Entity.Bug;
import com.BugReportingSystem.Entity.Project;

@Component
public interface BugService {
	List<Bug> getAllBugs();
	Bug saveBug(Bug bug);
	Bug getBugById(int id);
	Bug updateBug(Bug bug);
	void deleteBugById(int id);
	List<Bug> findAllByProject(Project project);
}
