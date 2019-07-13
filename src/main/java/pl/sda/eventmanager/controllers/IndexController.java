package pl.sda.eventmanager.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping
@Controller
public class IndexController {


    @GetMapping("/")
    public ModelAndView get() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }
}
