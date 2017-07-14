package library.controllers;

import library.domain.User;
import library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController
{

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("users", userService.listAllUsers());
        System.out.println("Returning users:");
        return "users";
    }

    @RequestMapping("user/{id}")
    public String showProduct(@PathVariable Integer id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "usershow";
    }

    @RequestMapping("user/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "registration";
    }

    @RequestMapping("user/new")
    public String newProduct(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String saveUser(User user){

        userService.addUser(user);

        return "redirect:/user/" + user.getId();
    }

}
