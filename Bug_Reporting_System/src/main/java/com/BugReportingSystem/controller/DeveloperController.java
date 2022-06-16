package com.BugReportingSystem.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BugReportingSystem.Entity.Team;
import com.BugReportingSystem.Entity.User;
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
	@RequestMapping("/dashboard")
	public String devdash(Principal p,Model m)
	{
		List<Team> team=teamser.findAllByUser(userser.getUserByUserName(p.getName()));
		m.addAttribute("teams",team.size());
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
}
