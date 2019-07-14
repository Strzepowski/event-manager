package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.dto.LoginForm;
import pl.sda.eventmanager.services.LoginValidator;
import pl.sda.eventmanager.services.RegisterValidator;

@Controller
public class LoginController {

    private final LoginValidator loginValidator;

    public LoginController(LoginValidator loginValidator) {
        this.loginValidator = loginValidator;
    }

    @GetMapping("login")
    public ModelAndView loginGet() {
        return new ModelAndView("login", "loginForm", new LoginForm());
    }

    @PostMapping("login")
    public ModelAndView loginPost(@ModelAttribute LoginForm loginForm, BindingResult bindingResult) {

        loginValidator.validate(loginForm, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
            return modelAndView;
        }
        return new ModelAndView("forward:/login");
    }
}
