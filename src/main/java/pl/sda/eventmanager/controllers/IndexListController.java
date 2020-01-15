package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.sda.eventmanager.model.Event;
import pl.sda.eventmanager.services.EventService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexListController {

    private final EventService eventService;

    public IndexListController(EventService eventService) {
        this.eventService = eventService;
    }

    @ModelAttribute("events")
    public List<Event> getEventList() {
       return eventService.findAllByOrderByEventEnd();
    }
}
