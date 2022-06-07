package com.BugReportingSystem.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BugReportingSystem.Entity.User;
import com.BugReportingSystem.Repository.UserRepository;
import com.BugReportingSystem.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepo;
	public UserServiceImpl(UserRepository obj) {
		this.userRepo=obj;
	}

	@Override
	public List<User> getAllUser() {
		
		return userRepo.findAll();
	}

	@Override
	public User saveUser(User user) {
		
		return userRepo.save(user);
	}

	@Override
	public User updateUser(User user) {
		
		return userRepo.save(user);
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return userRepo.findById(id).get();
	}

	@Override
	public void deleteUserById(int id) {
		userRepo.deleteById(id);
		
	}
	
}
