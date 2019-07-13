package pl.sda.eventmanager.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {

@GetMapping("/logout")
    public ModelAndView logoutGet(Authentication auth /*, HttpServletRequest httpServletRequest*/) /*throws ServletException*/ {
    ModelAndView modelAndView = new ModelAndView("index");
    modelAndView.addObject("auth", ((UserDetails) auth.getPrincipal()).getUsername());
    /*httpServletRequest.logout();*/
    return modelAndView;
}

}
