package com.BugReportingSystem.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BugReportingSystem.Entity.Bug;
import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;
import com.BugReportingSystem.Service.BugService;
import com.BugReportingSystem.Service.ProjectService;
import com.BugReportingSystem.Service.TeamService;
import com.BugReportingSystem.Service.UserService;
/**
* @author	Vishal Sonar
* @since	1.0
*/
@Controller
@RequestMapping("/qa")
public class QaController {
	/**
	 * Object Ref. of ProjectService interface which provides various methods to perform crud operations.
	 */
	@Autowired
	private ProjectService projectService;
	/**
	 * Object Ref. of UserService interface which provides various methods to perform crud operations.
	 */
	@Autowired
	private UserService userService;
	/**
	 * Object Ref. of TeamService interface which provides various methods to perform crud operations.
	 */
	@Autowired
	private TeamService teamService;
	/**
	 * Object Ref. of BugService interface which provides various methods to perform crud operations.
	 */
	@Autowired
	private BugService bugService;
	/**
	 *  '/dashboard' Request Mapping.
	 *  <p> Passes various model attributes to show data on Dashboard.html page.
	 *  @param	model add attributes of model
	 *  @param	principle provides user name and password of already login user
	 * 	@return QaDash.html file path
	 */
	@GetMapping("/dashboard")
	public String home(Model model,Principal p)
	{
		model.addAttribute("teams",teamService.teamsCountByUser(userService.getUserByUserName(p.getName())));
		model.addAttribute("projects",projectService.projectCountByUser(userService.getUserByUserName(p.getName())));
		model.addAttribute("user",userService.getUserByUserName(p.getName()));
		model.addAttribute("dash",true);
		return "/tester/QaDash";
	}
	/**
	 *  '/teams' Request Mapping.
	 *  <p> To view all the teams of User.
	 *  @param	model add attributes of model
	 *  @param	principle provides user name and password of already login user
	 * 	@return UserTeams.html file path
	 */
	@GetMapping("/teams")
	public String teams(Principal p,Model model)
	{
		model.addAttribute("teams",teamService.viewTeamsByUser(p));
		return "/tester/UserTeams";
	}
	/**
	 *  '/teams/viewmem/{id}' Request Mapping.
	 *  <p>Shows all the members of teams of user.
	 *  @param	model add attributes of model 
	 *  @param	principle provides user name and password of already login user
	 *  @param	teamid id of team of user
	 * 	@return ViewMembers.html file path
	 */
	@GetMapping("/teams/viewmem/{id}")
	public String viewTeamMembers(@PathVariable("id") int teamid,Model model)
	{
		model.addAttribute("users",userService.findAllByTeam(teamService.getTeamById(teamid)));
		model.addAttribute("teamname",teamService.getTeamById(teamid).getTeamName());
		return "/tester/ViewMembers";
	}
	/**
	 *  '/projects' Request Mapping.
	 *  <p> To view all the projects of User.
	 *  @param	model add attributes of model
	 *  @param	principle provides user name and password of already login user
	 * 	@return UserProjects.html file path
	 */
	@GetMapping("/projects")
	public String projects(Principal p,Model model)
	{
		model.addAttribute("projects",projectService.findAllByUser(userService.getUserByUserName(p.getName())));
		return "/tester/UserProjects";
	}
	/**
	 *  '/profile/show' Request Mapping.
	 *  <p> To view profile of User.
	 *  @param	model add attributes of model
	 *  @param	principle provides user name and password of already login user
	 * 	@return UserProfile.html file path
	 */
	@GetMapping("/profile/show")
	public String profile(Principal p,Model model)
	{
		model.addAttribute("user",userService.getUserByUserName(p.getName()));
		return "/tester/UserProfile";
	}
	
	/**
	 *  '/project/raiseBug/{pid}' Request Mapping.
	 *  <p> Gives form to create bug.
	 *  @param	model add attributes of model
	 *  @param	principle provides user name and password of already login user
	 *  @param	projectid id of project of user
	 * 	@return CreateBug.html file path
	 */
	@GetMapping("/project/raiseBug/{pid}")
	public String createRaisebug(@PathVariable("pid") int projectid,Model model,Principal p)
	{
		List<Team> teams=teamService.findAllByProject(projectService.getProjectById(projectid));
		Set<User> users=userService.findAllDevelopersByTeams(teams);
		model.addAttribute("users",users);
		model.addAttribute("projectid",projectid);
		model.addAttribute("testerid",userService.getUserByUserName(p.getName()).getId());
		return "/tester/CreateBug";
	}
	/**
	 *  '/raiseBug/add' Request Mapping.
	 *  <p> To raise the bug against specific project.
	 *  @param	bug object of bug contains all information
	 * 	@return redirect to '/qa/bug/show' requesta
	 */
	@PostMapping("/raiseBug/add")
	public String raiseBugSubmit(@ModelAttribute("bug") Bug bug)
	{
		bugService.saveBug(bug);
		return "redirect:/qa/bug/show";
	}
	/**
	 *  '/bug/show' Request Mapping.
	 *  <p> Shows all pending bugs.
	 *  @param	model add attributes of model
	 *  @param	principle provides user name and password of already login user
	 * 	@return ViewBugs.html file path
	 */
	@GetMapping("/bug/show")
	public String viewBugs(Principal p,Model model)
	{
		model.addAttribute("bugs",bugService.getBugByTester(userService.getUserByUserName(p.getName()),"Developer"));
		
		return "/tester/ViewBugs";
	}
	/**
	 *  '/bug/fixedBugs' Request Mapping.
	 *  <p> Shows all Fixed bugs.
	 *  @param	model add attributes of model
	 *  @param	principle provides user name and password of already login user
	 * 	@return FixedBugs.html file path
	 */
	@GetMapping("/bug/fixedBugs")
	public String fixedBugs(Principal p,Model model)
	{
		model.addAttribute("bugs",bugService.getBugByTester(userService.getUserByUserName(p.getName()),"Fixed"));
		
		return "/tester/FixedBugs";
	}
	/**
	 *  '/bug/fullInfo/{bid}' Request Mapping.
	 *  <p> provides full information of specific bug.
	 *  @param	model add attributes of model
	 *  @param	bugid id of specific bug
	 * 	@return BugDesc.html file path
	 */
	@GetMapping("/bug/fullInfo/{bid}")
	public String viewBugDescription(@PathVariable("bid") int bugid,Model model)
	{
		model.addAttribute("bug",bugService.getBugById(bugid));
		return "/tester/BugDesc";
	}
	/**
	 *  '/bug/delete/{bid}' Request Mapping.
	 *  <p> Delete's specific bug.
	 *  @param	bugid id of specific bug
	 * 	@return redirect to '/qa/bug/show' request
	 */
	@GetMapping("/bug/delete/{bid}")
	public String deleteBug(@PathVariable("bid") int bugid)
	{
		bugService.deleteBugById(bugid);
		return "redirect:/qa/bug/show";
	}
	/**
	 *  '/bug/edit/{bid}' Request Mapping.
	 *  <p> provides form to update the bug.
	 *  @param	bid id of specific bug
	 *  @param	model add attributes of model
	 * 	@return EditBug.html file path
	 */
	@GetMapping("/bug/edit/{bid}")
	public String editBug(@PathVariable("bid") int bid,Model model)
	{
		model.addAttribute("bug",bugService.getBugById(bid));
		return "/tester/EditBug";
	}
	/**
	 *  '/bug/edit/{bid}' Request Mapping.
	 *  <p> update's specific bug.
	 *  @param	bug object of Bug which have information
	 * 	@return redirect's to '/qa/bug/show' request.
	 */
	@PostMapping("/bug/update")
	public String updateBug(@ModelAttribute("bug") Bug bug)
	{
		Bug exbug=bugService.getBugById(bug.getId());
		exbug.setBugTitle(bug.getBugTitle());
		exbug.setBugDiscription(bug.getBugDiscription());
		
		bugService.updateBug(exbug);
		return "redirect:/qa/bug/show";
	}
	/**
	 *  '/bug/fixed/{bid}' Request Mapping.
	 *  <p> Changes the status of  specific bug to Fixed.
	 *  @param	bugid id of bug
	 * 	@return redirect's to '/qa/bug/show' request.
	 */
	@GetMapping("/bug/fixed/{bid}")
	public String fixBug(@PathVariable("bid") int bugid)
	{
		Bug bug=bugService.getBugById(bugid);
		bug.setBugStatus("Fixed");
		bugService.saveBug(bug);
		return "redirect:/qa/bug/show";
	}
}
