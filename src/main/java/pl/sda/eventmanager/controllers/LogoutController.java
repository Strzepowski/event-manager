package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutController {

@GetMapping("/logout")
    public ModelAndView logoutGet(){
    ModelAndView modelAndView = new ModelAndView("logout");
    return modelAndView;
}

}
