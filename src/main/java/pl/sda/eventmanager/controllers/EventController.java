package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.services.EventService;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("event")
    public ModelAndView getEvent(@RequestParam(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("event");
        modelAndView.addObject("singleEvent", eventService.findById(id).get());
        return modelAndView;
    }
}
