package library.domain.validator;

import library.domain.form.FormChangePassword;
import library.domain.form.FormRegistration;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Brandon on 8/15/2017.
 */
@Component
public class UserChangePasswordValidator implements Validator{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateFormValidator.class);
    private final UserService userService;

    @Autowired
    public UserChangePasswordValidator(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz)
    {
        return clazz.equals(FormChangePassword.class);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        LOGGER.debug("Validating {}", target);
        FormChangePassword user = (FormChangePassword) target;
        validatePasswords(errors, user);
    }

    private void validatePasswords(Errors errors, FormChangePassword form)
    {
        if (!form.getPassword().equals(form.getPasswordRepeat()))
        {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

}
