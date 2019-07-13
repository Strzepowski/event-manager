package pl.sda.eventmanager.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.services.SecurityService;


@RequestMapping
@Controller
public class IndexController {


    @GetMapping("/")
    public ModelAndView get(Authentication auth) {
        ModelAndView modelAndView = new ModelAndView("index");

//        if(auth != null){
//        modelAndView.addObject("auth", ((UserDetails) auth.getPrincipal()).getUsername());}

        return modelAndView;
    }
}
