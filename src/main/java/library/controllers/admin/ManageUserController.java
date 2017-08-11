package library.controllers.admin;
import library.domain.User;
import library.domain.form.FormUserAccountOnlyIdNumber;
import library.domain.form.FormEditUserRole;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Brandon on 8/10/2017.
 */
@Controller
public class ManageUserController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "manage/user", method = RequestMethod.GET)
    String load(Model model){
        model.addAttribute("userList", userService.getAllUsers());
        return "admin/admin_all_users";
    }

    @RequestMapping(value = "manage/user/save", method = RequestMethod.POST)
    public String UpdateUserRole(@Valid @ModelAttribute("newUser") FormEditUserRole newUser, BindingResult bindingResult)
    {
        System.out.println("ID NUMBER IS: " + newUser.getIdNumber());
        System.out.println("ROLE IS " + newUser.getRole());
        System.out.println(String.format("Processing user create form=%s, bindingResult=%s", newUser, bindingResult));

        if (bindingResult.hasErrors())
        {
            // failed validation
            return "redirect:/manage/user/";
        }

        try
        {
            User temp = userService.getUserByIdNumber(newUser.getIdNumber());
            temp.setRole(newUser.getRole());
            System.out.println("Temp user " + temp.getRole() );
            userService.save(temp);
        }
        catch (DataIntegrityViolationException e)
        {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate user", e);
            bindingResult.reject("user.exist", "Material already exists");
            return "admin/admin_all_users";
        }
        // ok, redirect
        return "redirect:/manage/user";
    }

    @RequestMapping(value = "manage/user/unlock", method = RequestMethod.POST)
    public String UpdateUserStatus(@Valid @ModelAttribute("newUser") FormUserAccountOnlyIdNumber newUser, BindingResult bindingResult)
    {
        System.out.println("ID NUMBER IS: " + newUser.getIdNumber());
        System.out.println(String.format("Processing user create form=%s, bindingResult=%s", newUser, bindingResult));

        if (bindingResult.hasErrors())
        {
            // failed validation
            return "redirect:/manage/user/";
        }

        try
        {
            User temp = userService.getUserByIdNumber(newUser.getIdNumber());
            temp.setLocked(false);
            System.out.println("Temp user " + temp.getRole() );
            userService.save(temp);
        }
        catch (DataIntegrityViolationException e)
        {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate user", e);
            bindingResult.reject("user.exist", "Material already exists");
            return "admin/admin_all_users";
        }
        // ok, redirect
        return "redirect:/manage/user";
    }

    @RequestMapping(value = "manage/user/delete", method = RequestMethod.POST)
    public String DeleteUser(@Valid @ModelAttribute("delUser") FormUserAccountOnlyIdNumber delUser, BindingResult bindingResult)
    {
        System.out.println("ID NUMBER IS: " + delUser.getIdNumber());
        System.out.println(String.format("Processing user create form=%s, bindingResult=%s", delUser, bindingResult));

        if (bindingResult.hasErrors())
        {
            // failed validation
            return "redirect:/manage/user";
        }

        try
        {
            System.out.println("Attempting to Delete - ManageUserController");
            userService.deleteByIdNumber(delUser.getIdNumber());
        }
        catch (DataIntegrityViolationException e)
        {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate user", e);
            bindingResult.reject("user.exist", "Material     already exists");
            return "admin/admin_all_users";
        }
        // ok, redirect
        return "redirect:/manage/user";
    }
}
