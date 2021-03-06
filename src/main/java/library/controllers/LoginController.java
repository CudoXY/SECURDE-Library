package library.controllers;

import library.domain.helper.UserHelper;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class LoginController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(HttpServletRequest request, @RequestParam Optional<String> error, Model model) {

		if (UserHelper.getCurrentUser(userService) != null)
			return "redirect:/";

		String referrer = request.getHeader("Referer");
		request.getSession().setAttribute("url_prior_login", referrer);

		model.addAttribute("error", error);
		LOGGER.debug("Getting login page, error={}", error);
		return "user/login";
	}
}
