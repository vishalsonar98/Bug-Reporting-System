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

@Controller
@RequestMapping("/qa")
public class QaController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private BugService bugService;
	
	@GetMapping("/dashboard")
	public String home(Model model,Principal p)
	{
		model.addAttribute("teams",teamService.teamsCountByUser(userService.getUserByUserName(p.getName())));
		model.addAttribute("projects",projectService.projectCountByUser(userService.getUserByUserName(p.getName())));
		model.addAttribute("user",userService.getUserByUserName(p.getName()));
		model.addAttribute("dash",true);
		return "/tester/QaDash";
	}
	
	@GetMapping("/teams")
	public String teams(Principal p,Model model)
	{
		model.addAttribute("teams",teamService.viewTeamsByUser(p));
		return "/tester/UserTeams";
	}
	
	@GetMapping("/projects")
	public String projects(Principal p,Model model)
	{
		model.addAttribute("projects",projectService.findAllByUser(userService.getUserByUserName(p.getName())));
		return "/tester/UserProjects";
	}
	
	@GetMapping("/profile/show")
	public String profile(Principal p,Model model)
	{
		model.addAttribute("user",userService.getUserByUserName(p.getName()));
		return "/tester/UserProfile";
	}
	
	@GetMapping("/teams/viewmem/{id}")
	public String viewTeamMembers(@PathVariable("id") int teamid,Model model)
	{
		model.addAttribute("users",userService.findAllByTeam(teamService.getTeamById(teamid)));
		model.addAttribute("teamname",teamService.getTeamById(teamid).getTeamName());
		return "/tester/ViewMembers";
	}
	
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
	
	@PostMapping("/raiseBug/add")
	public String raiseBugSubmit(@ModelAttribute("bug") Bug bug)
	{
		bugService.saveBug(bug);
		return "redirect:/qa/bug/show";
	}
	
	@GetMapping("/bug/show")
	public String viewBugs(Principal p,Model model)
	{
		model.addAttribute("bugs",bugService.getBugByTester(userService.getUserByUserName(p.getName())));
		
		return "/tester/ViewBugs";
	}
	@GetMapping("/bug/fullInfo/{bid}")
	public String viewBugDescription(@PathVariable("bid") int bugid,Model model)
	{
		model.addAttribute("bug",bugService.getBugById(bugid));
		return "/tester/BugDesc";
	}
	
	@GetMapping("/bug/delete/{bid}")
	public String deleteBug(@PathVariable("bid") int bugid)
	{
		bugService.deleteBugById(bugid);
		return "redirect:/qa/bug/show";
	}
}
