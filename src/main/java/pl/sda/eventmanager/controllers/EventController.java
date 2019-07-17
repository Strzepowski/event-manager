package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.dto.EventForm;
import pl.sda.eventmanager.services.EventService;
import pl.sda.eventmanager.services.EventValidator;

@Controller
public class EventController extends IndexListController {

    private final EventValidator eventValidator;
    private final EventService eventService;

    public EventController(EventValidator eventValidator, EventService eventService) {
        super(eventService);
        this.eventValidator = eventValidator;
        this.eventService = eventService;
    }

    @GetMapping("/addEvent")
    public ModelAndView addEventGet() {
        return new ModelAndView("addevent", "eventForm", new EventForm());
    }


    @PostMapping("/addEvent")
    public ModelAndView addEventPost(@ModelAttribute EventForm eventForm, BindingResult bindingResult) {
        eventValidator.validate(eventForm, bindingResult);

        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("addevent");
            modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
            return modelAndView;
        }

        eventService.addEvent(eventForm);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("eventAdded", eventForm.getEventName());
        modelAndView.addObject("events", getEventList());
        return modelAndView;
    }
}
