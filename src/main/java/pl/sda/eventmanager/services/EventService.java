package pl.sda.eventmanager.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.eventmanager.dto.EventForm;
import pl.sda.eventmanager.model.Event;
import pl.sda.eventmanager.model.User;
import pl.sda.eventmanager.repositories.EventRepository;

import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Optional<Event> findByEventName(String eventName) {
        return eventRepository.findByEventName(eventName);
    }

    @Transactional
    public void addEvent(EventForm eventForm) {
        eventForm.setEventOrganiser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        final Event myEvent = Event.EventBuilder
                .anEvent()
                .withEventName(eventForm.getEventName())
                .withEventStart(eventForm.getEventStart())
                .withEventEnd(eventForm.getEventEnd())
                .withEventDescription(eventForm.getEventDescription())
                .withEventOrganiser(eventForm.getEventOrganiser())
                .build();

        eventRepository.save(myEvent);
    }
}
