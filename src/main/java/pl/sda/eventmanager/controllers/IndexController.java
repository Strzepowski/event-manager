package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.services.EventService;

@RequestMapping
@Controller
public class IndexController extends IndexListController {

    public IndexController(EventService eventService) {
        super(eventService);
    }

    @GetMapping("/")
    public ModelAndView get() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("events", getEventList());
        return modelAndView;
    }
}
