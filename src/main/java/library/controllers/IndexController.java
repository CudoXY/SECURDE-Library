package library.controllers;

import library.domain.Role;
import library.domain.User;
import library.domain.helper.UserHelper;
import library.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;

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
		if (redirectFromRole())
			return "redirect:/manage/dashboard";

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth = " + auth);
		return "user/homepage";
	}

	// Sample Usage
	// TODO: Delete this after
	@RequestMapping("/completeRegistration")
	String completeRegistration(HttpServletRequest request, final RedirectAttributes redirectAttributes)
	{
		// Auto
		int id = UserHelper.getCurrentUser(userService).getId();
		UserHelper.logoutUser(request);
		redirectAttributes.addFlashAttribute("id", id);
		return "redirect:/confirmaccount";
	}

	private boolean redirectFromRole()
	{
		User u = UserHelper.getCurrentUser(userService);

		return u != null && (u.getRole() == Role.ROLE_STAFF || u.getRole() == Role.ROLE_MANAGER || u.getRole() == Role.ROLE_ADMIN);
	}
}
