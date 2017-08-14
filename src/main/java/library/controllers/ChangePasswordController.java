package library.controllers;

import library.domain.User;
import library.domain.form.FormChangePassword;
import library.domain.form.FormResetPassword;
import library.domain.form.FormSecretQuestion;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Brandon on 8/14/2017.
 */
@Controller
public class ChangePasswordController {
    @Autowired
    private UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/change", method = RequestMethod.GET)
    public ModelAndView getForgetPageID(@RequestParam Optional<String> error, @RequestParam String id) {
        LOGGER.debug("Getting change temporary password id page, error={}", error);
        return new ModelAndView("user/tempaccountpasswordreset", "id" , id);
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public String checkSecretAnswer(@Valid @ModelAttribute("changePass") final FormChangePassword formChangePassword,
                                    final BindingResult bindingResult, final RedirectAttributes redirectAttributes)
    {
        User user = userService.getUserById(formChangePassword.id);
        if (bindingResult.hasErrors())
        {
            // failed validation
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.form", bindingResult);
            redirectAttributes.addFlashAttribute("form", formChangePassword);
            return "redirect:/forgot";
        }

        try
        {
            if(user.getPassword().equals(formChangePassword.origPassword.trim()))
                if((formChangePassword.newPassword.trim()).equals(formChangePassword.repNewPassword.trim())){
                    user.setPassword(formChangePassword.getRepNewPassword().trim());
                    userService.save(user);
                }

            return "redirect:/login";
        }
        catch (DataIntegrityViolationException e)
        {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            bindingResult.reject("email.exists", "Email already exists");
            return "user/register";
        }
    }
}
