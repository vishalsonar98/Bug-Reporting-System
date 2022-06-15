package com.BugReportingSystem.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;
import com.BugReportingSystem.Repository.UserRepository;
import com.BugReportingSystem.Service.UserService;

/*
* service class implements UserService interface and overrides various methods of UserService to 
* perform various operations of User entity with the help of UserRepository.
*/
@Service
public class UserServiceImpl implements UserService {
	
	/*
	 * object ref. to UserRepository
	 */
	@Autowired
	UserRepository userRepo;

	/*
	 * UserServiceImpl class constructor to initialize object to userRepo ref.
	 */

	/*
	 * overriding all the methods of UserService interface
	 */
	
	/*
	 * this method uses findAll method of UserRepository
	 * 
	 * @return list of users
	 */		
	@Override
	public List<User> getAllUser() {
		
		return userRepo.findAll();
	}

	/*
	 * this method uses save method of UserRepository 
	 * 
	 * @return object of saved information of user
	 */
	@Override
	public User saveUser(User user) {
		
		return userRepo.save(user);
	}
	/*
	 * this method uses save method of UserRepository 
	 * 
	 * @return object of updated information of user
	 */
	@Override
	public User updateUser(User user) {
		
		return userRepo.save(user);
	}
	
	/*
	 * this method uses findById method of UserRepository 
	 * 
	 * @return object of particular user information
	 */
	@Override
	public User getUserById(int id) {
		
		return userRepo.findById(id).get();
	}
	
	/*
	 * this method uses deleteById method of UserRepository 
	 */
	@Override
	public void deleteUserById(int id) {
		userRepo.deleteById(id);
		
	}
	
	/*
	 * this method uses count method of UserRepository 
	 * 
	 * @return total number of users inside the user entity 
	 */
	@Override
	public int empCount() {
		
		
		return (int) userRepo.count();
	}
	/*
	 * this method uses getUserByUserName method of UserRepository 
	 * 
	 * @return object of particular user
	 */
	@Override
	public User getUserByUserName(String email) {
		
		return userRepo.getUserByUserName(email);
	}

	@Override
	public List<User> findAllByTeam(Team team) {
		
		return userRepo.findAllByTeam(team);
	}
	
}
