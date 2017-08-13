package library.controllers;

import library.domain.User;
import library.domain.form.FormRegistration;
import library.domain.helper.UserHelper;
import library.domain.validator.UserCreateFormValidator;
import library.services.currentuser.CurrentUserService;
import library.services.secret_question.SecretQuestionService;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.util.GregorianCalendar;

@Controller
public class RegisterController
{
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private UserService userService;
	private SecretQuestionService secretQuestionService;
	private UserCreateFormValidator userCreateFormValidator;
	private CurrentUserService currentUserService;

	@Autowired
	public void setCurrentUserService(CurrentUserService currentUserService)
	{
		this.currentUserService = currentUserService;
	}

	@Autowired
	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	@Autowired
	public void setSecretQuestionService(SecretQuestionService secretQuestionService)
	{
		this.secretQuestionService = secretQuestionService;
	}

	@Autowired
	public void setUserCreateFormValidator(UserCreateFormValidator userCreateFormValidator)
	{
		this.userCreateFormValidator = userCreateFormValidator;
	}

	@InitBinder("user")
	public void initBinder(WebDataBinder binder)
	{
		binder.addValidators(userCreateFormValidator);
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getUserCreatePage(Model model)
	{
		if (UserHelper.getCurrentUser(userService) != null)
			return "redirect:/";

		LOGGER.debug("Getting user create form");
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", new FormRegistration());
		}
		model.addAttribute("questionList", secretQuestionService.getAll());
		return "user/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String handleUserCreateForm(@Valid @ModelAttribute("user") final FormRegistration form, final BindingResult bindingResult,
									   final RedirectAttributes redirectAttributes)
	{
		User u = new User();
		u.setFirstName(form.getFirstName());
		u.setMiddleName(form.getMiddleName());
		u.setLastName(form.getLastName());
		u.setRole(form.getRole());
		u.setId(form.getId());
		u.setPassword(form.getPasswordRepeat());
		System.out.println("form.getPasswordRepeat() = " + form.getPasswordRepeat());
		System.out.println("u.setPassword = " + u.getPassword());
		u.setEmail(form.getEmail());
		u.setSecretQuestion(form.getSecretQuestion());
		u.setSecretAnswer(form.getSecretAnswer());

		GregorianCalendar c = new GregorianCalendar(form.getYear(), form.getMonth() - 1, form.getDay());
		Date birthDate = new Date(c.getTimeInMillis());
		u.setBirthDate(birthDate);
		u.setDateRegistered(new Date(new java.util.Date().getTime()));
		System.out.println(String.format("Processing user create form=%s, bindingResult=%s", form, bindingResult));

		if (bindingResult.hasErrors())
		{
			// failed validation
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
			redirectAttributes.addFlashAttribute("user", form);
			return "redirect:/register";
		}

		try
		{
			userService.save(u);

			currentUserService.autologin(u.getId() + "", u.getPassword());
		}
		catch (DataIntegrityViolationException e)
		{
			// probably email already exists - very rare case when multiple admins are adding same user
			// at the same time and form validation has passed for more than one of them.
			LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
			bindingResult.reject("email.exists", "Email already exists");
			return "user/register";
		}
		// ok, redirect
		return "redirect:/";
	}
}
