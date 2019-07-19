package pl.sda.eventmanager.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.eventmanager.dto.EventForm;
import pl.sda.eventmanager.model.Event;
import pl.sda.eventmanager.model.User;
import pl.sda.eventmanager.repositories.EventRepository;
import pl.sda.eventmanager.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public EventService(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public Optional<Event> findByEventName(String eventName) {
        return eventRepository.findByEventName(eventName);
    }

    public List<Event> findAllOngoingAndFutureEvents() {
        return eventRepository.findAll();
    }

    @Transactional
    public void addEvent(EventForm eventForm) {
        final Event myEvent = Event.builder()
                .eventName(eventForm.getEventName())
                .eventStart(eventForm.getEventStart())
                .eventEnd(eventForm.getEventEnd())
                .eventDescription(eventForm.getEventDescription())
                .eventOrganiser(userRepository.findByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail()).get())
                .build();

        eventRepository.save(myEvent);
    }

    //TODO LIST IS NOT SORTED ON "/"

    public List<Event> sortEventList(List<Event> events) {
        events.stream()
                .filter(x -> x.getEventEnd().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Event::getEventEnd).reversed())
                .collect(Collectors.toList());
        return events;
    }
}
