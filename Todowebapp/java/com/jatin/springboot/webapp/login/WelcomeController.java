package com.jatin.springboot.webapp.login;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("name" )
public class WelcomeController {

//	//http://localhost:8080/login?name=Yash
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String gottoWelcomePage(ModelMap model) {
		// @RequestParam String name, ModelMap model as parameter in gotoLoginPage
		model.put("name",getLoggedinUsername());
		return "welcome";
	}
	
	private String getLoggedinUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
		
	}
}