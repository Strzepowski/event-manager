package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.dto.RegisterForm;
import pl.sda.eventmanager.services.UserService;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("register")
    public ModelAndView registerNewUser() {

        return new ModelAndView("register", "registerForm", new RegisterForm());
    }

    @PostMapping("register")
    public String registerPost(@ModelAttribute RegisterForm registerForm) {

        userService.save(registerForm.getEmail(), registerForm.getPassword(), registerForm.getUsername());

        return "redirect:/";
    }
}
