package com.BugReportingSystem.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BugReportingSystem.Entity.User;
import com.BugReportingSystem.Service.UserService;

/*
* Signin Controller controls all the request for Sign in and
* provides response or request mapping for each request. 
*/
@Controller
public class SigninController {
	/*
	 * user service object ref. to perform crud operations on user.
	 */
	@Autowired
	private UserService ser;

	/*
	 * Request mapping of "/" which provides Dashborad page after successful login.
	 * 
	 * @return redirect to "/admin/dashboard" , "/developer/dashboard" ,
	 * "/tester/dashboard" , "/signin" as per condition
	 */		
	@RequestMapping("/")
	public String pageredirect(Principal principal)
	{
		
		if(ser.getUserByUserName(principal.getName()).getUserTypeId().getUserTypeId()==255)
		{
			
			return "redirect:/admin/dashboard";
		}
		else if(ser.getUserByUserName(principal.getName()).getUserTypeId().getUserTypeId()==101)
		{
			return "redirect:/developer/dashboard";
		}
		else if(ser.getUserByUserName(principal.getName()).getUserTypeId().getUserTypeId()==102)
		{
			return "redirect:/tester/dashboard";
		}
		System.out.println(principal.getName()+" user role printed");
		return "redirect:/signin";
	}

	/*
	 * Requset Mapping of "/signin" request
	 * 
	 * @return login.html page
	 */		
	@RequestMapping("/signin")
	public String signin()
	{
		return "login";
	}

	
}
