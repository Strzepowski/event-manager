package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminPanelController {

    @GetMapping("adminpanel")
    public ModelAndView get() {
        return new ModelAndView("adminpanel");
    }
}
