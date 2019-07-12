package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.dto.LoginForm;
import pl.sda.eventmanager.services.SecurityService;
import pl.sda.eventmanager.services.UserValidator;


@Controller
public class LoginController {

    private final SecurityService securityService;
    private final UserValidator userValidator;


    public LoginController(SecurityService securityService, UserValidator userValidator) {
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @GetMapping("login")
    public ModelAndView loginGet() {

        ModelAndView modelAndView = new ModelAndView("login", "loginForm", new LoginForm());


        return modelAndView;
    }


    @PostMapping("login")
    public ModelAndView loginPost(@ModelAttribute LoginForm loginForm, BindingResult bindingResult) {

        userValidator.validateLogin(loginForm, bindingResult);


        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
            return modelAndView;
        }

        securityService.login(loginForm.getEmail(), loginForm.getPassword());

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("loggedUser", securityService.findLoggedInUsername());

        return modelAndView;
    }
}
