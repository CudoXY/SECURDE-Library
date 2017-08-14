package library.controllers;

import library.domain.User;
import library.domain.form.FormResetPassword;
import library.domain.form.FormSecretAnswer;
import library.domain.form.FormSecretQuestion;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
 * Created by Brandon on 8/11/2017.
 */
@Controller
public class PasswordController {

    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/forget", method = RequestMethod.GET)
    public ModelAndView getForgetPageID(@RequestParam Optional<String> error) {
        LOGGER.debug("Getting forget password id page, error={}", error);
        return new ModelAndView("user/forgetpasswordID", "error", error);
    }

    @RequestMapping(value = "/forget", method = RequestMethod.POST)
    public ModelAndView setForgetPageID(@RequestParam(value = "id") String id) {
        System.out.println("FORGET ID NUMBEER: " + id);
        String sq = userService.getUserById(Integer.parseInt(id)).getSecretQuestion().getQuestion();
        FormSecretQuestion formSQ = new FormSecretQuestion();
        formSQ.setId(Integer.parseInt(id));
        formSQ.setSecretQuestion(sq);
        System.out.println(formSQ.getSecretQuestion());
        return new ModelAndView("user/forgetpasswordSQ","formSQ", formSQ);
    }

    @RequestMapping(value = "/forgetpass", method = RequestMethod.POST)
    public ModelAndView checkSecretAnswer(@Valid @ModelAttribute("form") final FormSecretAnswer formSecretAnswer,
                                          final BindingResult bindingResult, final RedirectAttributes redirectAttributes)
    {
        formSecretAnswer.setSecretAnswer(formSecretAnswer.getSecretAnswer().trim());
        LOGGER.debug("Getting user create form");
        if (bindingResult.hasErrors())
        {
            // failed validation
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.form", bindingResult);
            redirectAttributes.addFlashAttribute("form", formSecretAnswer);
            return new ModelAndView("user/forgetpasswordID");
        }
        try
        {
            if(formSecretAnswer.getSecretAnswer().equals(userService.getUserById(formSecretAnswer.id).getSecretAnswer())){
                return new ModelAndView("user/resetpassword","id", formSecretAnswer.getId());
            }
            return new ModelAndView("user/forgetpasswordID");
        }
        catch (DataIntegrityViolationException e)
        {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            bindingResult.reject("email.exists", "Email already exists");
            return new ModelAndView("user/forgetpasswordID");
        }
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String checkSecretAnswer(@Valid @ModelAttribute("resetPass") final FormResetPassword formResetPassword,
                                    final BindingResult bindingResult, final RedirectAttributes redirectAttributes)
    {
        User user = userService.getUserById(formResetPassword.getId());
        user.setPassword(formResetPassword.getPassword());
        System.out.println(user.getId());
        System.out.println(formResetPassword.getPassword());
        if (bindingResult.hasErrors())
        {
            // failed validation
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.form", bindingResult);
            redirectAttributes.addFlashAttribute("form", formResetPassword);
            return "redirect:/forget";
        }

        try
        {
            userService.save(user);
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
