package com.BugReportingSystem.Service;

import java.util.List;

import com.BugReportingSystem.Entity.User;


public interface UserService {
	List<User> getAllUser();

	User saveUser(User user);

	User updateUser(User user);

	User getUserById(int id);

	void deleteUserById(int id);
}
