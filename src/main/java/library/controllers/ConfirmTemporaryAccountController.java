package library.controllers;

import library.domain.User;
import library.domain.form.FormConfirmTempAccount;
import library.domain.validator.UserConfirmFormValidator;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.util.GregorianCalendar;

@Controller
public class ConfirmTemporaryAccountController
{
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private UserService userService;
	private SecretQuestionService secretQuestionService;
	private UserConfirmFormValidator userConfirmFormValidator;
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
	public void setUserConfirmFormValidator(UserConfirmFormValidator userConfirmFormValidator)
	{
		this.userConfirmFormValidator = userConfirmFormValidator;
	}

	@InitBinder("user")
	public void initBinder(WebDataBinder binder)
	{
		binder.addValidators(userConfirmFormValidator);
	}

	@RequestMapping(value = "/confirmaccount", method = RequestMethod.GET)
	public String getUserCreatePage(Model model, final RedirectAttributes redirectAttributes, @ModelAttribute("id") int id)
	{
		LOGGER.debug("Getting user create form");
		model.addAttribute("user", new FormConfirmTempAccount());
		model.addAttribute("id",id);
		model.addAttribute("questionList", secretQuestionService.getAll());
		System.out.println(id);
		return "user/confirmtempacccount";
	}

	@RequestMapping(value = "/confirmaccount", method = RequestMethod.POST)
	public String handleUserConfirmForm(@Valid @ModelAttribute("user") final FormConfirmTempAccount form, final BindingResult bindingResult,
									   final RedirectAttributes redirectAttributes)
	{
		User u = userService.getUserById(form.getId());
		u.setFirstName(form.getFirstName());
		u.setMiddleName(form.getMiddleName());
		u.setLastName(form.getLastName());
		u.setPassword(form.getPasswordRepeat());
		u.setEmail(form.getEmail());
		u.setSecretQuestion(form.getSecretQuestion());
		u.setSecretAnswer(form.getSecretAnswer());
		u.setTemporary(false);

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
			return "redirect:/confirmaccount";
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
			return "user/confirmaccount";
		}
		// ok, redirect
		return "redirect:/";
	}
}
