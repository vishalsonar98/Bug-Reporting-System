package com.BugReportingSystem.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BugReportingSystem.Entity.Project;
import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;
import com.BugReportingSystem.Service.ProjectService;
import com.BugReportingSystem.Service.TeamService;
import com.BugReportingSystem.Service.UserService;

@Controller
@RequestMapping("/developer")
public class DeveloperController 
{
	@Autowired
	private UserService userser;
	
	@Autowired
	private TeamService teamser;
	
	@Autowired
	private ProjectService projectser;
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
}
