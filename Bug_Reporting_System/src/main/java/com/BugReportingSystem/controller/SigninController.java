package com.BugReportingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BugReportingSystem.Entity.User;
import com.BugReportingSystem.Entity.UserRole;
import com.BugReportingSystem.Service.UserService;

@Controller
public class SigninController {


	@RequestMapping("/signin")
	public String signin()
	{
		return "login";
	}

	
}
