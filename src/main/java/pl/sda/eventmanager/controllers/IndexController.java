package pl.sda.eventmanager.controllers;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.services.SecurityService;


@RequestMapping
@Controller
public class IndexController {

    private final SecurityService securityService;

    public IndexController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/")
    public ModelAndView get() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("loggedUser", securityService.findLoggedInUsername());
        return modelAndView;
    }
}
