package library.controllers;

import library.domain.User;
import library.domain.form.FormChangePassword;
import library.domain.form.FormConfirmTempAccount;
import library.domain.form.FormResetPassword;
import library.domain.form.FormSecretQuestion;
import library.domain.helper.UserHelper;
import library.domain.validator.UserChangePasswordValidator;
import library.domain.validator.UserConfirmFormValidator;
import library.services.currentuser.CurrentUserService;
import library.services.secret_question.SecretQuestionService;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

/**
 * Created by Brandon on 8/14/2017.
 */
@Controller
public class ChangePasswordController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private UserService userService;
    private UserChangePasswordValidator userChangePasswordValidator;
    private CurrentUserService currentUserService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public void setUserChangeFormValidator(UserChangePasswordValidator userChangeFormValidator)
    {
        this.userChangePasswordValidator = userChangeFormValidator;
    }

    @InitBinder("user")
    public void initBinder(WebDataBinder binder)
    {
        binder.addValidators(userChangePasswordValidator);
    }

    @RequestMapping(value = "/changepass", method = RequestMethod.GET)
    public String getUserChangePage(Model model)
    {
        LOGGER.debug("Getting change password form");
        model.addAttribute("changePass", new FormChangePassword());
        return "user/changepassword";
    }

    @RequestMapping(value = "/changepass", method = RequestMethod.POST)
    public String handleUserConfirmForm(@Valid @ModelAttribute("user") final FormChangePassword form, final BindingResult bindingResult,
                                        final RedirectAttributes redirectAttributes)
    {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        int id  = UserHelper.getCurrentUser(userService).getId();
        User u = userService.getUserById(id);
        System.out.println("USER" + u.getId() + u.getFirstName());
        if (bindingResult.hasErrors())
        {
            // failed validation
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", form);
            return "redirect:/login";
        }

        try
        {
            System.out.println(form.origPassword);
            System.out.println(u.getPassword());
            if (bCryptPasswordEncoder.matches(form.origPassword, u.getPassword())) {
                u.setPassword(form.getPasswordRepeat().trim());
                userService.save(u);
                System.out.println("I'm in boys");
            } else {
                bindingResult.reject("Incorrrect original password", "Incorrect password");
            }
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
