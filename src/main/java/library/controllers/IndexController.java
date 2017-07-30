package library.controllers;

import library.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController
{

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}


	@RequestMapping("/")
	String index()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth = " + auth);
		return "user/homepage";
	}

	@RequestMapping("/reserve")
	String reserve()
	{
		return "user/roomreserve";
	}
}
