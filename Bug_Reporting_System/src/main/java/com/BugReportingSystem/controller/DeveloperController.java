package com.BugReportingSystem.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BugReportingSystem.Entity.Bug;
import com.BugReportingSystem.Entity.Project;
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
@RequestMapping("/developer")
public class DeveloperController 
{
	/**
	 * Object Ref. of UserService interface which provides various methods to perform crud operations.
	 */
	@Autowired
	private UserService userser;
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
	 *  '/dashboard' Request Mapping.
	 *  <p> Passes various model attributes to show data on Dashboard.html page.
	 *  @param	model add attributes of model
	 *  @param	principle provides user name and password of already login user
	 * 	@return Dashboard.html file path
	 */
	@RequestMapping("/dashboard")
	public String devdash(Principal p,Model m)
	{
		List<Team> teams=teamser.findAllByUser(userser.getUserByUserName(p.getName()));
		m.addAttribute("teams",teams.size());
		
		
		List<Team> team=teamser.findAllByUser(userser.getUserByUserName(p.getName()));
		Set<Project> projects=new HashSet<Project>();
		for (int i = 0; i < team.size(); i++) 
		{
			projects.addAll(projectser.findAllByTeam(team.get(i)));
		}
		m.addAttribute("projects",projects.size());
		
		m.addAttribute("user",userser.getUserByUserName(p.getName()));
		m.addAttribute("dash",true);
		//'/'
		return "/developer/DeveloperDash";
	}
	@RequestMapping("/navbar")
	public String navbar()
	{
		return "/developer/Navbar";
	}
	@GetMapping("/profile/show")
	public String profile(Principal p,Model m)
	{
		m.addAttribute("user",userser.getUserByUserName(p.getName()));
		return "/developer/UserProfile";
	}
	
	@GetMapping("/teams")
	public String teams(Principal p,Model m)
	{
		List<Team> team=teamser.findAllByUser(userser.getUserByUserName(p.getName()));
		m.addAttribute("teams",team);
		return "/developer/UserTeams";
	}
	
	@GetMapping("/teams/viewmem/{id}")
	public String viewmembers(@PathVariable("id") int teamid,Model m)
	{
		List<User> users=userser.findAllByTeam(teamser.getTeamById(teamid));
		m.addAttribute("users",users);
		m.addAttribute("teamname",teamser.getTeamById(teamid).getTeamName());
		return "/developer/ViewMembers";
	}
	
	@GetMapping("/projects/viewprojects")
	public String projects(Model m,Principal p)
	{
		List<Team> team=teamser.findAllByUser(userser.getUserByUserName(p.getName()));
		Set<Project> projects=new HashSet<Project>();
		for (int i = 0; i < team.size(); i++) 
		{
			projects.addAll(projectser.findAllByTeam(team.get(i)));
		}
		m.addAttribute("projects",projects);
		return "/developer/UserProjects";
	}
	
	@GetMapping("/bugs/show")
	public String viewBugs(Principal p,Model model)
	{
		List<Bug> bugs=bugService.findAllByUser(userser.getUserByUserName(p.getName()));
		
		model.addAttribute("bugs",bugService.getDeveloperStatusBug(bugs));
		return "/developer/ShowBugs";
	}
	
	@GetMapping("/bug/submit/{bid}")
	public String submitBug(@PathVariable("bid") int bugid)
	{
		Bug bug=bugService.getBugById(bugid);
		bug.setBugStatus("Fixed");
		
		bugService.updateBug(bug);
		return "redirect:/developer/bugs/show";
	}
}
