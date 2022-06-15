package com.BugReportingSystem.Service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;

/*
* UserService interface Provides abstract methods to perform crud operations on user entity
*/
@Component
public interface UserService {
	
	List<User> getAllUser();

	User saveUser(User user);
	
	User getUserByUserName(String email);

	User updateUser(User user);

	User getUserById(int id);

	void deleteUserById(int id);
	
	int empCount();
	
	List<User> findAllByTeam(Team team);
}
