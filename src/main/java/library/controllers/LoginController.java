package library.controllers;

import library.domain.User;
import library.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController
{
	private BorrowService borrowService;

	@Autowired
	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	String load(@CookieValue(value = "userId", defaultValue = "-1") int userId, Model model){

		if (userId != -1)
			return "redirect:/";

		model.addAttribute("user", new User());

		return "user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	String login(User user, HttpServletResponse response){

		Cookie cookie = new Cookie("userId", user.getId() + "");
		cookie.setPath("/MyApplication");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
		response.addCookie(cookie);

		return "redirect:/";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	String logout(HttpServletRequest request, HttpServletResponse response)
	{
		Cookie cookie = new Cookie("userId", null); // Not necessary, but saves bandwidth.
		cookie.setPath("/MyApplication");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
		response.addCookie(cookie);

		return "user/login";
	}
}
