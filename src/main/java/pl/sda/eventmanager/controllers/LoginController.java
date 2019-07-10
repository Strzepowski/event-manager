package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.dto.LoginForm;
import pl.sda.eventmanager.services.UserService;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public ModelAndView login(){
        return new ModelAndView("login", "loginForm", new LoginForm());
    }
}
