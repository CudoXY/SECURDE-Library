package library.domain.validator;

import library.domain.User;
import library.domain.form.FormCreateTempAccount;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FormCreateTempAccountValidator implements Validator
{

	private static final Logger LOGGER = LoggerFactory.getLogger(FormCreateTempAccountValidator.class);
	private final UserService userService;

	@Autowired
	public FormCreateTempAccountValidator(UserService userService)
	{
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz)
	{
		return clazz.equals(User.class);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		LOGGER.debug("Validating {}", target);
		FormCreateTempAccount user = (FormCreateTempAccount) target;
		validatePasswords(errors, user);
		validateIdNumber(errors, user);
	}

	private void validatePasswords(Errors errors, FormCreateTempAccount form)
	{
		if (!form.getPassword().equals(form.getPasswordRepeat()))
		{
			errors.reject("password.no_match", "Passwords do not match");
		}
	}

	private void validateIdNumber(Errors errors, FormCreateTempAccount form)
	{
		if (userService.getUserById(form.getId()) != null)
		{
			errors.reject("username.exists", "User with this username already exists");
		}
	}
}
