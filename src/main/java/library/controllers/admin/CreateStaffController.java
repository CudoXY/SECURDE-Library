package library.controllers.admin;

import library.domain.form.FormCreateTempAccount;
import library.domain.User;
import library.domain.validator.FormCreateTempAccountValidator;
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

import javax.validation.Valid;
import java.sql.Date;

/**
 * Created by Brandon on 8/10/2017.
 */
@Controller
public class CreateStaffController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private UserService userService;
    private FormCreateTempAccountValidator formCreateTempAccountValidator;

    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @InitBinder("user")
    public void initBinder(WebDataBinder binder)
    {
        binder.addValidators(formCreateTempAccountValidator);
    }

    @RequestMapping(value = "manage/user/new", method = RequestMethod.GET)
    public String getUserCreatePage(Model model)
    {
        LOGGER.debug("Getting user create form");
        model.addAttribute("user", new FormCreateTempAccount());
        return "admin/admin_create_staff";
    }

    @RequestMapping(value = "manage/user/new", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("user") FormCreateTempAccount user, BindingResult bindingResult)
    {
        User newUser = new User();
        newUser.setId(user.getIdNumber());
        newUser.setPassword(user.getPassword());
        newUser.setPasswordRepeat(user.getPasswordRepeat());
        newUser.setRole(user.getRole());
        newUser.setDateRegistered(new Date(new java.util.Date().getTime()));
        newUser.setLocked(false);
        newUser.setTemporary(true);

        System.out.println(String.format("Processing user create form=%s, bindingResult=%s", user, bindingResult));

        if (bindingResult.hasErrors())
        {
            // failed validation
            return "admin/admin_create_staff";
        }

        try
        {
            userService.save(newUser);
        }
        catch (DataIntegrityViolationException e)
        {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate username", e);
            return "admin/admin_create_staff";
        }
        // ok, redirect
        return "redirect:/manage/dashboard";
    }
}
