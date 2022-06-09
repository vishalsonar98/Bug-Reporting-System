package com.BugReportingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/developer")
public class DeveloperController 
{
	@RequestMapping("/dashboard")
	public String devdash()
	{
		return "DeveloperDash";
	}
}
