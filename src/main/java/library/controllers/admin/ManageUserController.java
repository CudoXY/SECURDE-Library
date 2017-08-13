package library.controllers.admin;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}
