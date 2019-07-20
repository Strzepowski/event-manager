package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.services.EventService;

import java.time.LocalDateTime;

@Controller
public class EventSearchController {

    private final EventService eventService;

    public EventSearchController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView eventSearch(@RequestParam(value = "eventName", required = false) String eventName,
                                    @RequestParam(value = "eventFilter") /*TODO ENUM*/  String eventFilter) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("searchedEventName", eventName);
        modelAndView.addObject("eventFilter", eventFilter);
        if (eventFilter.equals("Future Events")) {
            modelAndView.addObject("events", eventService.findAllByEventNameContainingAndEventStartAfterOrderByEventEnd(eventName, LocalDateTime.now()));
        }

        if (eventFilter.equals("Ongoing Events")) {
            modelAndView.addObject("events", eventService.findAllByEventNameContainingAndEventStartBeforeOrderByEventEnd(eventName, LocalDateTime.now()));
        }

        if (eventFilter.equals("All Events")) {
            modelAndView.addObject("events", eventService.findAllByEventNameContainingOrderByEventEnd(eventName));
        }
        return modelAndView;
    }


}
