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
import com.BugReportingSystem.Service.BugService;
import com.BugReportingSystem.Service.ProjectService;
import com.BugReportingSystem.Service.TeamService;
import com.BugReportingSystem.Service.UserService;

/**
* @author	Vishal Sonar
* @since	1.0
*/
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	/**
	 * Password encoder to encript password of users.
	 */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * Object Ref. of UserService interface which provides various methods to perform crud operations.
	 */
	@Autowired
	private UserService ser;
	/**
	 * Object Ref. of TeamService interface which provides various methods to perform crud operations.
	 */
	@Autowired
	private TeamService teamser;
	/**
	 * Object Ref. of ProjectService interface which provides various methods to perform crud operations.
	 */
	@Autowired
	private ProjectService projectser;
	/**
	 * Object Ref. of BugService interface which provides various methods to perform crud operations.
	 */
	@Autowired
	private BugService bugService;

	/**
	 * Request mappign for "/" 
	 * @return redirects to '/admin/dashboard' request.
	 */		
	@RequestMapping("/")
	public String index()
	{
		return "redirect:/admin/dashboard";
	}
	

	/**
	 *	'/Header' request mapping.
	 * 	<p>This method redirects to the Header.html page which is a 
	 * 	<p>common page for all the other html pages of admin.
	 * 	@return Hearder.html file path
	 */
	@GetMapping("/Header")
	public String home()
	{
		
		return "Header";
	}

	
	
	/**
	 *  '/dashboard' Request Mapping.
	 *  <p> Passes various model attributes to show data on Dashboard.html page.
	 *  @param	model add attributes of model
	 *  @param	principle provides user name and password of already login user
	 * 	@return Dashboard.html file path
	 */
	@GetMapping("/dashboard")
	public String dashboard(Model model,Principal principle)
	{
		User user=ser.getUserByUserName(principle.getName());
		model.addAttribute("name",user.getFirstName()+" "+user.getLastName());
		model.addAttribute("email",user.getEmail());
		model.addAttribute("dash",true);
		model.addAttribute("title","Dashboard");
		model.addAttribute("empcount",ser.empCount());
		model.addAttribute("teamCount",teamser.teamCount());
		model.addAttribute("projectCount",projectser.ProjectCount());
		model.addAttribute("bugCount",bugService.bugCount());
		
		return "Dashboard";
	}
	
	/**
	 *  '/profile/show' Request Mapping.
	 *  <p> This method provides request mapping for AdminProfile.html page which demonstrates the profile of user
	 *  @param	model add attributes of model
	 *  @param	principle provides user name and password of already login user
	 * 	@return Dashboard.html file path
	 */
	@RequestMapping("/profile/show")
	public String adminProfile(Model m,Principal p)
	{
		m.addAttribute("user",ser.getUserByUserName(p.getName()));
		return "AdminProfile";
	}
	
	
	@GetMapping("/profile/changePassword/{id}")
	public String adminProfilePasswordResetPage(@PathVariable("id") int id,Model model)
	{
		model.addAttribute("user",ser.getUserById(id));
		return "ChangePassword";
	}
	
	@PostMapping("/profile/changePassword/reset")
	public String adminProfilePasswordReset(@ModelAttribute("user") User user,@RequestParam("password1") String password1,Model model)
	{
		if (!(password1.contains(user.getPassword()))) {
			model.addAttribute("condition",true);
			return "ChangePassword";
		}
		else {
			model.addAttribute("condition",false);
			User exuser=ser.getUserById(user.getId());
			exuser.setPassword(passwordEncoder.encode(user.getPassword()));
			ser.saveUser(exuser);
			return "redirect:/admin/employees";
		}
		
	}
	/**
	 * '/employees' Request Mapping.
	 * <p>This method provides request mapping for Employees.html page which shows the information of employees
	 * <p> Shows information of employees added by admin
	 *  @param	model add attributes of model
	 *  @return Employees.html file path
	 */
	@GetMapping("/employees")
	public String employees(Model m)
	{
		m.addAttribute("title","Employees");
		m.addAttribute("users",ser.getAllUser());
		return "Employees";
	}
	
	/**
	 * '/addemployee' Request Mapping.
	 * <p>This method provides request mapping for AddEmployee page which provides form to add new employees
	 * @param	model add attributes of model
	 * @return AddEmployee.html file path
	 */
	@GetMapping("/addemployee")
	public String addEmployee(Model m)
	{
		m.addAttribute("user",new User());
		m.addAttribute("title","Add Employee");
		return "AddEmployee";
	}
	
	/**
	 *	'/addemployee/add' Request Mapping.
	 *	<p> This method get called if and only if admin click the submit button of add employee form
	 *	<p> This method saves all the data which provided by admin in User Entity and in user table
	 * 	@param	user stores the user object which contains all information of employee
	 * 	@param	role stores the user role id which which is get from the form.
	 * 	@param	userRole its a reference variable of object of UserRole entity
	 * 	@param	model add attributes of model
	 *  @return redirect's to '/admin/employees' request
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

	/**
	 *	'/employee/{id}' Request Mapping.
	 * 	<p>if admin press Delete button of Employees page this request send by browser.
	 * 	<p>This Request deletes data of particular employee and id of that employee passed
	 * 	<p>in request.
	 * 	<p>This method performs delete action by using methods of UserServises Interface.
	 * 	@param	id contains id of employee which we want to delete from the database
	 *	@return redirect request to "/admin/employees"
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

	/**
	 * 	'/employee/update/{id}' Request Mapping.
	 * 	<p>If admin press Update button of Employee page this request is send by
	 * 	<p>browser.
	 * 	<p>In this request we provied 'id' of perticular employee and then
	 * 	<p>assign that id to Model attribute after that we redirects admin to
	 * 	<p>UpdateEmployee page.
	 * 	@param	id contains id of employee('user') which we want to update
	 * 	@param	model add attributes of model
	 *  @return UpdateEmployee.html file path
	 */
	@RequestMapping("/employee/update/{id}")
	public String updateEmp(@PathVariable int id,Model m)
	{
		m.addAttribute("employee",ser.getUserById(id));
		return "UpdateEmployee";
	}

	/**
	 * 	'/employee/{id}' Request Mapping.
	 * 	<p>This method provides functionality of update data of employees or users.
	 * 	<p>First we fatch all the data of employee from the form and then insert those data in databse
	 * 	<p>by using 'saveUser' method of UserService Interface.
	 * 	@param	user stores the user object which contains all information of employee
	 * 	@param	id contains id of employee('user') which we want to update
	 * 	@param	model add attributes of model
	 * 	@param	role stores the user role id which which is get from the form.
	 * 	@param	userRole its a reference variable of object of UserRole entity
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
	/**
	 * 	'/teams' Request Mapping.
	 * 	<p>This method gives the Teams.html page
	 * 	<p>Teams.html shows all the teams created by admin
	 * 	@param	model add attributes of model
	 *  @return Teams.html page
	 */
	@RequestMapping("/teams")
	public String teams(Model m)
	{
		m.addAttribute("title","Teams");
		m.addAttribute("teams",teamser.getAllTeams());
		return "Teams";
	}
	/**
	 * 	'/addteam' Request Mapping.
	 * 	<p>This method gives the AddTeam.html page
	 * 	<p>AddTeam.html page provides from to add new team in database
	 * 	@param	model add attributes of model
	 *  @return AddTeam.html page
	 */
	@RequestMapping("/addteam")
	public String addTeam(Model m)
	{
		m.addAttribute("team",new Team());
		return "AddTeam";
	}
	/**
	 * 	'/addteam/add' Request Mapping.
	 * 	<p>This method saves the team in database which is provided by admin
	 * 	<p>AddTeam.html page provides from to add new team in database
	 * 	@param	team stores the object of 'Team' entity which contains name of team
	 *  @return redirect's to '/admin/teams' request
	 */
	@PostMapping("/addteam/add")
	public String addTeamAdd(@ModelAttribute("team") Team team)
	{
		teamser.saveTeam(team);
		return "redirect:/admin/teams";
	}
	/**
	 * 	'/team/addemployee/{id}' Request Mapping.
	 * 	<p>This method filter the employees. Employees which are already present in team are not shows in  AddEmpInTeam.html page.
	 * 	@param	model add attributes of model
	 * 	@param	id store id of team 
	 *  @return AddEmpInTeam.html page
	 */
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
	/**
	 * 	'/team/addemployee/{id}/{teamid}' Request Mapping.
	 * 	<p>This method get called if the admin click on add buttton.
	 * 	<p>It add's employee in the team 
	 * 	@param	empid store employee id
	 * 	@param	teamid store id of team 
	 *  @return redirect to '/admin/team/addemployee/{teamid}' request
	 */
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
	/**
	 * 	'/team/showmem/{id}' Request Mapping.
	 * 	<p>This method passes all the information of team members to ShowTeamMem.html page
	 * 	<p>Html page shows all the information of employees which are the part of particular team.
	 * 	@param	teamid store id of team 
	 * 	@param	model add attributes of model
	 *  @return ShowTeamMem.html page
	 */
	@GetMapping("/team/showmem/{id}")
	public String showMember(@PathVariable("id") int teamid,Model m)
	{
		m.addAttribute("teamid",teamid);
		m.addAttribute("team",teamser.getTeamById(teamid).getUser());
		return "ShowTeamMem";
	}
	/**
	 * 	'/team/removemember/{id}/{teamid}' Request Mapping.
	 * 	<p>This method removes the specific employee details from team or removes specific employee from team
	 * 	<p>When admin click on remove button then this method get called.
	 * 	@param	id store employee id
	 * 	@param	teamid store id of team 
	 *  @return redirect to '/admin/team/showmem/{teamid}' request.
	 */
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
	/**
	 * 	'/team/delete/{id}' Request Mapping.
	 * 	<p>This method delete team from database
	 * 	<p>When admin click on delete button then this method get called.
	 * 	@param	tid store id of team 
	 *  @return redirect to '/admin/teams' request.
	 */
	@GetMapping("/team/delete/{id}")
	public String deleteTeam(@PathVariable("id") int tid)
	{
		teamser.deleteTeamById(tid);
		return "redirect:/admin/teams";
	}
	/**
	 * 	'/team/rename/{id}' Request Mapping.
	 * 	<p>This method provides the RenameTeam.html page in page we user get form for rename particular team.
	 * 	@param	model add attributes of model
	 * 	@param	tid store id of team 
	 *  @return RenameTeam.html page
	 */
	@GetMapping("/team/rename/{id}")
	public String rename(Model m,@PathVariable("id") int tid)
	{
		m.addAttribute("team",teamser.getTeamById(tid));
		
		return "RenameTeam";
	}
	/**
	 * 	'/team/rename' Request Mapping.
	 * 	<p>This method Rename the team or updates the team name in database
	 * 	@param	team stores the object of 'Team' entity which contains name of team
	 *  @return redirect to '/admin/teams' request.
	 */
	@PostMapping("/team/rename")
	public String renameTeam(@ModelAttribute("team") Team team)
	{
		List<User> user=ser.findAllByTeam(team);
		team.setUser(user);
		teamser.saveTeam(team);
		
		return "redirect:/admin/teams";
	}
	/**
	 * 	'/addProject' Request Mapping.
	 * 	<p>This method provides AddProject.html page and pass model attribute of Project class object
	 * 	@param	model add attributes of model
	 *  @return AddProject.html page
	 */
	@RequestMapping("/addProject")
	public String addproject(Model m)
	{
		m.addAttribute("project",new Project());
		return "AddProject";
	}
	/**
	 * 	'/addProject/add' Request Mapping.
	 * 	<p>This method crates new project  in database
	 * 	<p>Also validates the form details when user click on submit button.
	 * 	@param	project stores the object of 'Project' entity which contains name of project
	 * 	@param	result stores error occur during validation
	 *  @return if the there are error of validation it will return AddProject.html page and shows error on page otherwise it will redirect to '/admin/currentprojects' request
	 */
	@PostMapping("/addProject/add")
	public String addProjectDb(@Valid @ModelAttribute("project") Project project,BindingResult result)
	{
		if (result.hasErrors()) {
			
			return "AddProject";
		}
		else
		{
			
			project.setProjectName(project.getProjectName());
			projectser.saveProject(project);
			return "redirect:/admin/currentprojects";
		}
	}

	/**
	 * 	'/currentprojects' Request Mapping.
	 * 	<p>This method provides information of active projects whose status is 'In-progress'
	 * 	@param	model add attributes of model
	 *  @return CurrentProjects.html page
	 */
	@GetMapping("/currentprojects")
	public String currentProjects(Model m)
	{
		m.addAttribute("title","Current Projects");
		m.addAttribute("projects", projectser.getAllByStatus(0));
		return "CurrentProjects";
	}
	/**
	 * 	'/completedprojects' Request Mapping.
	 * 	<p>This method provides information of completed projects whose status is 'completed'
	 * 	@param	model add attributes of model
	 *  @return CompletedProjects.html page
	 */
	@GetMapping("/completedprojects")
	public String completedProjects(Model m)
	{
		m.addAttribute("title","Current Projects");
		m.addAttribute("project1", projectser.getAllByStatus(1));
		return "CompletedProjects";
	}
	/**
	 * 	'/project/addTeams/{id}' Request Mapping.
	 * 	<p>filters the teams and provides the list of team which are not the part of current project
	 * 	@param	pid project id
	 * 	@param	model add attributes of model
	 *  @return AddTeamInProject.html page
	 */
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
	/**
	 * 	'/project/addteam/{projectid}/{teamid}' Request Mapping.
	 * 	<p>When method called it will add the particular team inside the project of assigns project to the team.
	 * 	<p>simply in database team id and project id get stored.
	 * 	@param	pid project id
	 * 	@param	tid team id
	 *  @return redirect to '/admin/project/addTeams/{projectid}' request
	 */
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
	/**
	 * 	'/project/viewTeams/{id}' Request Mapping.
	 * 	<p>this method provides the list of teams which are the part of particular project
	 * 	@param	id project id
	 * 	@param	model add attributes of model
	 *  @return TeamAssign.html page
	 */
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
	/**
	 * 	'/project/removeTeam/{teamid}/{projectid}' Request Mapping.
	 * 	<p>this method removes the team from project
	 * 	@param	tid team id
	 * 	@param	pid project id
	 *  @return redirect to '/admin/project/viewTeams/{projectid}' request
	 */
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
	/**
	 * 	'/project/rename/{id}' Request Mapping.
	 * 	<p>provides existing project information to html page 
	 * 	@param	pid project id
	 * 	@param	model add attributes of model
	 *  @return RenameProject.html page
	 */
	@GetMapping("/project/rename/{id}")
	public String renameProject(@PathVariable("id") int pid,Model m)
	{
		Project project=projectser.getProjectById(pid);
		m.addAttribute("project",project);
		
		return "RenameProject";
	}
	/**
	 * 	'/project/rename' Request Mapping.
	 * 	<p>update name of project in database
	 * 	@param	project stores the object of 'Project' entity which contains name of project
	 *  @return redirect to '/admin/currentprojects'
	 */
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
	/**
	 * 	'/project/delete/{id}' Request Mapping.
	 * 	<p>deletes project from the database with the help of project id
	 * 	@param	pid project id
	 *  @return redirect to '/admin/currentprojects'
	 */
	@GetMapping("/project/delete/{id}")
	public String deleteProject(@PathVariable("id") int pid)
	{
		projectser.deleteProjectById(pid);
		return "redirect:/admin/currentprojects";
	}
	/**
	 * 	'/project/editstatus/{id}' Request Mapping.
	 * 	<p>provides existing object of project to html page
	 * 	@param	id project id
	 * 	@param	model add attributes of model
	 *  @return ProjectStatus.html page
	 */
	@GetMapping("/project/editstatus/{id}")
	public String editStatus(@PathVariable("id") int id,Model m)
	{
		Project project=projectser.getProjectById(id);
		m.addAttribute("project",project);
		m.addAttribute("pid",id);
		return "ProjectStatus";
		
	}
	/**
	 * 	'/editstatus/{id}' Request Mapping.
	 * 	<p>Update the name of project in database
	 * 	@param	project stores the object of 'Project' entity which contains name of project
	 * 	@param	id project id
	 *  @return redirect to '/admin/currentprojects'
	 */
	@PostMapping("/editstatus/{id}")
	public String editStatusp(@ModelAttribute("project") Project project,@PathVariable("id") int id)
	{
		Project exproject=projectser.getProjectById(id);
		exproject.setStatus(project.getStatus());
		projectser.updateProject(exproject);
		System.out.println(project);
		return "redirect:/admin/currentprojects";
	}
	
	@GetMapping("/bugs/viewAll")
	public String viewBugs(Model model)
	{
		model.addAttribute("bugs",bugService.getAllBugs());
		return "Bugs";
	}
}
	
	
