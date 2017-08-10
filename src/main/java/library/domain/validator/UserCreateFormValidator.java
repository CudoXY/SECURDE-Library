package library.domain.validator;

import library.domain.form.FormRegistration;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserCreateFormValidator implements Validator
{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateFormValidator.class);
	private final UserService userService;

	@Autowired
	public UserCreateFormValidator(UserService userService)
	{
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz)
	{
		return clazz.equals(FormRegistration.class);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		LOGGER.debug("Validating {}", target);
		FormRegistration user = (FormRegistration) target;
		validatePasswords(errors, user);
		validateEmail(errors, user);
		validateIdNumber(errors, user);
	}

	private void validatePasswords(Errors errors, FormRegistration form)
	{
		if (!form.getPassword().equals(form.getPasswordRepeat()))
		{
			errors.reject("password.no_match", "Passwords do not match");
		}
	}

	private void validateEmail(Errors errors, FormRegistration form)
	{
		if (userService.getUserByEmail(form.getEmail()).isPresent())
		{
			errors.reject("email.exists", "User with this email already exists");
		}
	}

	private void validateIdNumber(Errors errors, FormRegistration form)
	{
		if (userService.getUserByIdNumber(form.getIdNumber()).isPresent())
		{
			errors.reject("id.exists", "User with this ID number already exists");
		}
	}
}
