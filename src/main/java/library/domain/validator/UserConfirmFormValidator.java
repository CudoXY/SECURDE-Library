package library.domain.validator;

import library.domain.form.FormConfirmTempAccount;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserConfirmFormValidator implements Validator
{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserConfirmFormValidator.class);
	private final UserService userService;

	@Autowired
	public UserConfirmFormValidator(UserService userService)
	{
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz)
	{
		return clazz.equals(FormConfirmTempAccount.class);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		LOGGER.debug("Validating {}", target);
		FormConfirmTempAccount user = (FormConfirmTempAccount) target;
		validatePasswords(errors, user);
		validateEmail(errors, user);
	}

	private void validatePasswords(Errors errors, FormConfirmTempAccount form)
	{
		if (!form.getPassword().equals(form.getPasswordRepeat()))
		{
			errors.reject("password.no_match", "Passwords do not match");
		}
	}

	private void validateEmail(Errors errors, FormConfirmTempAccount form)
	{
		if (userService.getUserByEmail(form.getEmail()) != null)
		{
			errors.reject("email.exists", "User with this email already exists");
		}
	}
}
