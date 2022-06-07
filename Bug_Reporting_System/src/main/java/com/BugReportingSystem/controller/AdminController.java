package com.BugReportingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BugReportingSystem.Entity.User;
import com.BugReportingSystem.Entity.UserRole;
import com.BugReportingSystem.Service.UserService;

@Controller
public class AdminController {
	
	UserService ser;
	
	public AdminController(UserService ser) {
		this.ser = ser;
	}
	@GetMapping("/Header")
	public String home()
	{
		return "Header";
	}
	@RequestMapping("/signin")
	public String signIn()
	{
		
		return "login";
	}
	@GetMapping("/currentprojects")
	public String currentProjects(Model m)
	{
		m.addAttribute("title","Current Projects");
		return "CurrentProjects";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model m)
	{
		m.addAttribute("title","Dashboard");
		return "Dashboard";
	}

	@GetMapping("/employees")
	public String employees(Model m)
	{
		m.addAttribute("title","Dashboard");
		m.addAttribute("users",ser.getAllUser());
		return "Employees";
	}
	@GetMapping("/addemployee")
	public String addEmployee(Model m)
	{
		
		m.addAttribute("title","Add Employee");
		return "AddEmployee";
	}
	@PostMapping("/addemployee/add")
	public String addemployeeimp(@ModelAttribute("user") User user,@RequestParam("Role") int role,UserRole userRole) //Core pojo
	{
		userRole.setUserTypeId(role);
		user.setUserTypeId(userRole);
		System.out.println(user);
		ser.saveUser(user);
		
		return "redirect:/employees";
	}
	
	@GetMapping("/employee/{id}")
	public String deleteUser(@PathVariable int id)
	{
		ser.deleteUserById(id);
		return "redirect:/employees";
	}
	
	@RequestMapping("/employee/update/{id}")
	public String updateEmp(@PathVariable int id,Model m)
	{
		m.addAttribute("employee",ser.getUserById(id));
		return "UpdateEmployee";
	}
	
	@PostMapping("/employee/{id}")
	public String updateEmployee(@ModelAttribute("employee") User user, @PathVariable int id,Model m,@RequestParam("Role") int role,UserRole userRole)
	{
		User existinguser=ser.getUserById(id);
		existinguser.setFirstName(user.getFirstName());
		existinguser.setLastName(user.getLastName());
		existinguser.setEmail(user.getEmail());
		existinguser.setPassword(user.getPassword());
		
		userRole.setUserTypeId(role);
		user.setUserTypeId(userRole);
		
		existinguser.setUserTypeId(user.getUserTypeId());
		ser.saveUser(existinguser);
		return "redirect:/employees";
	}
	
	@RequestMapping("/teams")
	public String teams(Model m)
	{
		m.addAttribute("title","Teams");
		return "Teams";
	}
	
	@RequestMapping("/form")
	public String form()
	{
		return "form";
	}
}
