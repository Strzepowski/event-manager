package pl.sda.eventmanager.controllers;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping
@Controller
public class IndexController {

    @GetMapping(value = {"/", "home"})
    public ModelAndView get(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("index");


        //NULL POINTER
        if(authentication.getPrincipal() != null){
        modelAndView.addObject("message", ((UserDetails) authentication.getPrincipal()).getUsername());}

        return modelAndView;
    }


}
