package com.BugReportingSystem.controller;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
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

import com.BugReportingSystem.Entity.Project;
import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;
import com.BugReportingSystem.Entity.UserRole;
import com.BugReportingSystem.Service.ProjectService;
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
	private UserService ser;
	
	@Autowired
	private TeamService teamser;
	
	@Autowired
	private ProjectService projectser;

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
	 * Admin Dashboard Request Mapping.
	 * @return Dashboard.html file path
	 */
	@GetMapping("/dashboard")
	public String dashboard(Model m,Principal p)
	{
		User user=ser.getUserByUserName(p.getName());
		m.addAttribute("name",user.getFirstName()+" "+user.getLastName());
		m.addAttribute("email",user.getEmail());
		m.addAttribute("dash",true);
		m.addAttribute("title","Dashboard");
		m.addAttribute("empcount",ser.empCount());
		m.addAttribute("teamCount",teamser.teamCount());
		m.addAttribute("projectCount",projectser.ProjectCount());
		
		return "Dashboard";
	}
	
	@RequestMapping("/profile/show")
	public String adminProfile(Model m,Principal p)
	{
		m.addAttribute("user",ser.getUserByUserName(p.getName()));
		return "AdminProfile";
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
	public String deleteEmployee(@PathVariable int id)
	{
		User user=ser.getUserById(id);
		List<Team> teams=teamser.findAllByUser(user);
		for (int i = 0; i < teams.size(); i++) 
		{
			Team team=teams.get(i);
			team.getUser().remove(user);
			user.getTeam().remove(team);
			
			teamser.saveTeam(team);
			ser.saveUser(user);
		}
		
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
		Team team=teamser.getTeamById(id);
		List<User> users=ser.getAllUser();
		List<User> teamUsers=ser.findAllByTeam(team);
		
		for (int i = 0; i < teamUsers.size(); i++) 
		{
			User teamUser1=teamUsers.get(i);
			users.remove(teamUser1);
		}
		
		m.addAttribute("employee",users);
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
		return "redirect:/admin/team/addemployee/{teamid}";
	}
	
	@GetMapping("/team/showmem/{id}")
	public String showMember(@PathVariable("id") int teamid,Model m)
	{
		m.addAttribute("teamid",teamid);
		m.addAttribute("team",teamser.getTeamById(teamid).getUser());
		return "ShowTeamMem";
	}
	@GetMapping("/team/removemember/{id}/{teamid}")
	public String removeMember(@PathVariable("id") int id,@PathVariable("teamid") int teamid)
	{
		Team team=teamser.getTeamById(teamid);
		User user=ser.getUserById(id);
		
		team.getUser().remove(user);
		user.getTeam().remove(team);
		ser.saveUser(user);
		teamser.saveTeam(team);
		return "redirect:/admin/team/showmem/{teamid}";
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
		List<User> user=ser.findAllByTeam(team);
		team.setUser(user);
		teamser.saveTeam(team);
		
		return "redirect:/admin/teams";
	}
	
	@RequestMapping("/addProject")
	public String addproject()
	{
		return "AddProject";
	}
	@PostMapping("/addProject/add")
	public String addProjectDb(@ModelAttribute("project") Project project)
	{
		project.setProjectName(project.getProjectName());
		projectser.saveProject(project);
		return "redirect:/admin/currentprojects";
	}

	/*
	 * Current Projects Request Mapping.
	 * @return CurrentProjects.html file path
	 */
	@GetMapping("/currentprojects")
	public String currentProjects(Model m)
	{
		m.addAttribute("title","Current Projects");
		m.addAttribute("projects", projectser.getAllByStatus(0));
		return "CurrentProjects";
	}
	
	@GetMapping("/completedprojects")
	public String completedProjects(Model m)
	{
		m.addAttribute("title","Current Projects");
		m.addAttribute("project1", projectser.getAllByStatus(1));
		return "CompletedProjects";
	}
	@GetMapping("/project/addTeams/{id}")
	public String addTeamsInProject(@PathVariable("id") int pid,Model m)
	{
		List<Team> team=teamser.getAllTeams();
		List<Team> teamProject=teamser.findAllByProject(projectser.getProjectById(pid));
		for (int i = 0; i < teamProject.size(); i++) {
			Team teamProject2=teamProject.get(i);
			team.remove(teamProject2);
		}	
		m.addAttribute("teams",team);
		m.addAttribute("projectid",pid);
		return "AddTeamInProject";
	}
	
	@GetMapping("/project/addteam/{projectid}/{teamid}")
	public String addTeamIntoProject(@PathVariable("projectid") int pid,@PathVariable("teamid") int tid)
	{
		int projectid=pid;
		Project project=projectser.getProjectById(pid);
		Team team=teamser.getTeamById(tid);
		
		project.getTeam().add(team);
		team.getProject().add(project);
		
		projectser.saveProject(project);
		teamser.saveTeam(team);
		return "redirect:/admin/project/addTeams/{projectid}";
	}
	
	@GetMapping("/project/viewTeams/{id}")
	public String viewTeams(@PathVariable("id") int id,Model m)
	{
		Project project=projectser.getProjectById(id);
		List<Team>teams=teamser.findAllByProject(project);
		if ((int)project.getStatus()==1) {
			m.addAttribute("completed",true);
		}
		else
		{
			m.addAttribute("completed",false);
		}
		m.addAttribute("teams",teams);
		m.addAttribute("pid",id);
		return "TeamAssign";
	}
	
	@GetMapping("/project/removeTeam/{teamid}/{projectid}")
	public String removeTeam(@PathVariable("teamid") int tid,@PathVariable("projectid") int pid)
	{
		int projectid=pid;
		Team team=teamser.getTeamById(tid);
		Project project=projectser.getProjectById(pid);
		team.getProject().remove(project);
		project.getTeam().remove(team);
		teamser.saveTeam(team);
		projectser.saveProject(project);
		return "redirect:/admin/project/viewTeams/{projectid}";
	}
	
	@GetMapping("/project/rename/{id}")
	public String renameProject(@PathVariable("id") int pid,Model m)
	{
		Project project=projectser.getProjectById(pid);
		m.addAttribute("project",project);
		
		return "RenameProject";
	}
	
	@PostMapping("/project/rename")
	public String renameProjectr(@ModelAttribute("project") Project project)
	{
		System.out.println(project);
		List<Team> team=teamser.findAllByProject(project);
		Project expro=projectser.getProjectById(project.getId());
		expro.setProjectName(project.getProjectName());
		expro.setTeam(team);
		projectser.updateProject(expro);
		
		return "redirect:/admin/currentprojects";
	}
	@GetMapping("/project/delete/{id}")
	public String deleteProject(@PathVariable("id") int pid)
	{
		projectser.deleteProjectById(pid);
		return "ProjectStatus";
	}
	
	@GetMapping("/project/editstatus/{id}")
	public String editStatus(@PathVariable("id") int id,Model m)
	{
		Project project=projectser.getProjectById(id);
		m.addAttribute("project",project);
		m.addAttribute("pid",id);
		return "ProjectStatus";
		
	}
	@PostMapping("/editstatus/{id}")
	public String editStatusp(@ModelAttribute("project") Project project,@PathVariable("id") int id)
	{
		Project exproject=projectser.getProjectById(id);
		exproject.setStatus(project.getStatus());
		projectser.updateProject(exproject);
		System.out.println(project);
		return "redirect:/admin/currentprojects";
	}
}
	
	
