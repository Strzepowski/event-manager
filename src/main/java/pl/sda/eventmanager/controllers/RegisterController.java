package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.dto.RegisterForm;
import pl.sda.eventmanager.services.UserService;
import pl.sda.eventmanager.services.UserValidator;

@Controller
public class RegisterController {

    private final UserService userService;
    private final UserValidator userValidator;

    public RegisterController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("register")
    public ModelAndView registerGet() {
        return new ModelAndView("register", "registerForm", new RegisterForm());
    }

    @PostMapping("register")
    public ModelAndView registerPost(@ModelAttribute RegisterForm registerForm, BindingResult bindingResult) {

        userValidator.validate(registerForm, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
            return modelAndView;
        }

        userService.saveUser(registerForm);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("accountCreated", registerForm.getEmail());

        return modelAndView;
    }
}