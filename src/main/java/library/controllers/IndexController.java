package library.controllers;

import library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
	String index(Model model, @CookieValue(value = "userId", defaultValue = "-1") int userId)
	{
		model.addAttribute("user", userService.getUserById(userId));
		return "user/homepage";
	}

	@RequestMapping("/reserve")
	String index1(Model model, @CookieValue(value = "userId", defaultValue = "-1") int userId)
	{
		model.addAttribute("user", userService.getUserById(userId));
		return "user/roomreserve";
	}
}
