package library.controllers;

import library.domain.User;
import library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Controller
public class RegisterController
{

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String saveProduct(int month, int day, int year, User user, HttpServletRequest request, HttpServletResponse response)
	{
		GregorianCalendar c = new GregorianCalendar(year, month, day);
		System.out.println(c.get(Calendar.MONTH) - 1);
		System.out.println(c.get(Calendar.DAY_OF_MONTH));
		System.out.println(c.get(Calendar.YEAR));
		Date birthDate = new Date(c.getTimeInMillis());
		user.setBirthDate(birthDate);

		System.out.println("saveProduct " + "POST");

		userService.addUser(user);

		Cookie cookie = new Cookie("userId", user.getId() + "");
		cookie.setPath("/MyApplication");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
		response.addCookie(cookie);

		return "redirect:/";
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String load(Model model)
	{
		model.addAttribute("user", new User());

		System.out.println("load " + "GET");
		return "user/register";
	}


}
