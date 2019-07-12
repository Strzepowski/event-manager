package pl.sda.eventmanager.controllers;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping
@Controller
public class IndexController {

    @GetMapping("/, home")
    public ModelAndView get() {
        ModelAndView modelAndView = new ModelAndView("index");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        modelAndView.addObject("loggedUser", authentication.getName());

        return modelAndView;
    }
}
