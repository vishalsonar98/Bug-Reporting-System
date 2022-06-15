package com.BugReportingSystem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;
import com.BugReportingSystem.Entity.UserRole;
import com.BugReportingSystem.Service.TeamService;
import com.BugReportingSystem.Service.UserService;

/*
* Admin Controller controls all the request for admin panel and
* provides response or request mapping for each request. 
*/
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	/*
	 * Password encoder to encript password of users.
	 */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/*
	 * Object Ref. of UserService interface which provides various methods to
	 * perform crud operations
	 */
	@Autowired
	UserService ser;
	
	@Autowired
	private TeamService teamser;

	public AdminController() {
	
	}

	/*
	 * Request mappign for "/" 
	 * @return admin dashboard
	 */		
	@RequestMapping("/")
	public String index()
	{
		return "redirect:/admin/dashboard";
	}
	

	/*
	 * Navigation bar HTML request mapping.
	 * @return Hearder.html file path
	 */
	@GetMapping("/Header")
	public String home()
	{
		return "Header";
	}

	
	/*
	 * Current Projects Request Mapping.
	 * @return CurrentProjects.html file path
	 */
	@GetMapping("/currentprojects")
	public String currentProjects(Model m)
	{
		m.addAttribute("title","Current Projects");
		return "CurrentProjects";
	}
	
	/*
	 * Admin Dashboard Request Mapping.
	 * @return Dashboard.html file path
	 */
	@GetMapping("/dashboard")
	public String dashboard(Model m)
	{
		m.addAttribute("title","Dashboard");
		m.addAttribute("empcount",ser.empCount());
		m.addAttribute("teamCount",teamser.teamCount());
		return "Dashboard";
	}
	
	/*
	 * Employees Page Request Mapping.
	 *  @return Employees.html file path
	 */
	@GetMapping("/employees")
	public String employees(Model m)
	{
		m.addAttribute("title","Employees");
		m.addAttribute("users",ser.getAllUser());
		return "Employees";
	}
	
	/*
	 * Add employee page Request Mapping.
	 * @return AddEmployee.html file path
	 */
	@GetMapping("/addemployee")
	public String addEmployee(Model m)
	{
		m.addAttribute("user",new User());
		m.addAttribute("title","Add Employee");
		return "AddEmployee";
	}
	
	/*
	 * When admin press submit button of form this request send by browser.
	 * This Request saves the information of new employee.
	 * After saving data admin get redirected to employees page.
	 *  @return redirect request to "/admin/employees" 
	 */
	@PostMapping("/addemployee/add")
	public String addemployeeimp(@ModelAttribute("user") User user,@RequestParam("Role") int role,UserRole userRole,Model m) //Core pojo
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
	 *  @return redirect request to "/admin/employees"
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
	 *  @return UpdateEmployee.html file path
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
	 *  @return redirect request to "/admin/employees"
	 */
	@PostMapping("/employee/{id}")
	public String updateEmployee( @ModelAttribute("employee") User user, @PathVariable int id,Model m,@RequestParam("Role") int role,UserRole userRole)
	{
		
		User existinguser=ser.getUserById(id);
		existinguser.setFirstName(user.getFirstName());
		existinguser.setLastName(user.getLastName());
		existinguser.setEmail(user.getEmail());
//		existinguser.setPassword(passwordEncoder.encode(user.getPassword()));
		
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
		m.addAttribute("teams",teamser.getAllTeams());
		return "Teams";
	}
	
	@RequestMapping("/addteam")
	public String addTeam(Model m)
	{
		m.addAttribute("team",new Team());
		return "AddTeam";
	}
	
	@PostMapping("/addteam/add")
	public String addTeamAdd(@ModelAttribute("team") Team team)
	{
		teamser.saveTeam(team);
		return "redirect:/admin/teams";
	}
	
	@GetMapping("/team/addemployee/{id}")
	public String addEmployeeTeam(Model m,@PathVariable("id") int id)
	{
		m.addAttribute("employee",ser.getAllUser());
		m.addAttribute("teamid",id);
		return "AddEmpInTeam";
	}
	
	@GetMapping("/team/addemployee/{id}/{teamid}")
	public String AddEmployeeInTeam(@PathVariable("id") int empid,@PathVariable("teamid") int teamid)
	{
		User user=ser.getUserById(empid);
		Team team=teamser.getTeamById(teamid);
		
		user.getTeam().add(team);
		team.getUser().add(user);
		
		ser.saveUser(user);
		teamser.saveTeam(team);
		return "redirect:/admin/teams";
	}
	
	@GetMapping("/team/showmem/{id}")
	public String showMember(@PathVariable("id") int teamid,Model m)
	{
		
		m.addAttribute("team",teamser.getTeamById(teamid).getUser());
		return "ShowTeamMem";
	}
	@GetMapping("/team/delete/{id}")
	public String deleteTeam(@PathVariable("id") int tid)
	{
		teamser.deleteTeamById(tid);
		return "redirect:/admin/teams";
	}
	@GetMapping("/team/rename/{id}")
	public String rename(Model m,@PathVariable("id") int tid)
	{
		m.addAttribute("team",teamser.getTeamById(tid));
		
		return "RenameTeam";
	}
	@PostMapping("/team/rename")
	public String renameTeam(@ModelAttribute("team") Team team)
	{
		
		teamser.saveTeam(team);
		return "redirect:/admin/teams";
	}
}
	
	
