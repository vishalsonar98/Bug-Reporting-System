package com.BugReportingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

/*
* Admin Controller controls all the request from the admin panel and
* provides response or request mapping for each request. 
*/
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("/")
	public String index()
	{
		return "redirect:/admin/dashboard";
	}
	
	/*
	 * Object Ref. of UserService interface which provides various methods to
	 * perform crud operations
	 */
	UserService ser;

	/*
	 * Paramiterized constructor which provides object to 'ser' ref.
	 */
	public AdminController(UserService ser) {
		this.ser = ser;
	}

	/*
	 * Navigation bar HTML request mapping.
	 */
	@GetMapping("/Header")
	public String home()
	{
		return "Header";
	}

	
	/*
	 * Current Projects Request Mapping.
	 */
	@GetMapping("/currentprojects")
	public String currentProjects(Model m)
	{
		m.addAttribute("title","Current Projects");
		return "CurrentProjects";
	}
	
	/*
	 * Admin Dashboard Request Mapping.
	 */
	@GetMapping("/dashboard")
	public String dashboard(Model m)
	{
		m.addAttribute("title","Dashboard");
		m.addAttribute("empcount",ser.empCount());
		return "Dashboard";
	}
	
	/*
	 * Employees Page Request Mapping.
	 */
	@GetMapping("/employees")
	public String employees(Model m)
	{
		m.addAttribute("title","Dashboard");
		m.addAttribute("users",ser.getAllUser());
		return "Employees";
	}
	
	/*
	 * Add employee page Request Mapping.
	 */
	@GetMapping("/addemployee")
	public String addEmployee(Model m)
	{
		
		m.addAttribute("title","Add Employee");
		return "AddEmployee";
	}
	
	/*
	 * When admin press submit button of form this request send by browser.
	 * This Request saves the information of new employee.
	 * After saving data admin get redirected to employees page.
	 */
	@PostMapping("/addemployee/add")
	public String addemployeeimp(@ModelAttribute("user") User user,@RequestParam("Role") int role,UserRole userRole) //Core pojo
	{
		userRole.setUserTypeId(role);
		user.setUserTypeId(userRole);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		System.out.println(user);
		ser.saveUser(user);
		
		return "redirect:/admin/employees";
	}

	/*
	 * if admin press Delete button of Employees page this request send by browser.
	 * This Request deletes data of particular employee and id of that employee passed
	 * in request.
	 * This method performs delete action by using methods of UserServises Interface.
	 */
	@GetMapping("/employee/{id}")
	public String deleteUser(@PathVariable int id)
	{
		ser.deleteUserById(id);
		return "redirect:/admin/employees";
	}

	/*
	 * If admin press Update button of Employee page this request is send by
	 * browser.
	 * In this request we provied 'id' of perticular employee and then
	 * assign that id to Model attribute after that we redirects admin to
	 * UpdateEmployee page.
	 */
	@RequestMapping("/employee/update/{id}")
	public String updateEmp(@PathVariable int id,Model m)
	{
		m.addAttribute("employee",ser.getUserById(id));
		return "UpdateEmployee";
	}

	/*
	 * This method provides functionality of update data of employees or users.
	 * First we fatch all the data of employee from the form and then insert those data in databse
	 * by using 'saveUser' method of UserService Interface.
	 */
	@PostMapping("/employee/{id}")
	public String updateEmployee(@ModelAttribute("employee") User user, @PathVariable int id,Model m,@RequestParam("Role") int role,UserRole userRole)
	{
		User existinguser=ser.getUserById(id);
		existinguser.setFirstName(user.getFirstName());
		existinguser.setLastName(user.getLastName());
		existinguser.setEmail(user.getEmail());
		existinguser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRole.setUserTypeId(role);
		user.setUserTypeId(userRole);
		
		existinguser.setUserTypeId(user.getUserTypeId());
		ser.saveUser(existinguser);
		return "redirect:/admin/employees";
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
