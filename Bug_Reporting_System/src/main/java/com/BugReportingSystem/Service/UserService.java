package com.BugReportingSystem.Service;

import java.util.List;

import com.BugReportingSystem.Entity.User;

/*
* UserService interface Provides abstract methods to perform crud operations on user entity
*/
public interface UserService {
	
	List<User> getAllUser();

	User saveUser(User user);
	
	User getUserByUserName(String email);

	User updateUser(User user);

	User getUserById(int id);

	void deleteUserById(int id);
	
	int empCount();
}
