package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.dto.LoginForm;
import pl.sda.eventmanager.services.SecurityService;


@Controller
public class LoginController {

    private final SecurityService securityService;

    public LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("login")
    public ModelAndView loginGet(){

        ModelAndView modelAndView = new ModelAndView("login", "loginForm", new LoginForm());



        return modelAndView;
    }


    // TODO
//    @PostMapping("login")
//    public ModelAndView loginPost(@ModelAttribute LoginForm loginForm, String error){
//
//    ModelAndView modelAndView = new ModelAndView(//TODO);
//        if (error != null)
//            modelAndView.addObject("error", "Your username and password is invalid.");
//
//        securityService.login(loginForm.getEmail(), loginForm.getPassword());
//
//        return modelAndView;
//    }
}
