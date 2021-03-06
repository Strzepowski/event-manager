package pl.sda.eventmanager.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.eventmanager.dto.CommentForm;
import pl.sda.eventmanager.dto.EventForm;
import pl.sda.eventmanager.model.Comment;
import pl.sda.eventmanager.model.Event;
import pl.sda.eventmanager.model.User;
import pl.sda.eventmanager.repositories.EventRepository;
import pl.sda.eventmanager.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
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

    //single event by name
    public Optional<Event> findByEventName(String eventName) {
        return eventRepository.findByEventName(eventName);
    }

    //single event by id
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    //future events containing
    public List<Event> findAllByEventNameContainingAndEventStartAfterOrderByEventEnd(String eventName, LocalDateTime dateNow) {
        return eventRepository.findAllByEventNameContainingAndEventStartAfterOrderByEventEnd(eventName, dateNow);
    }

    //ongoing events containing
    public List<Event> findAllByEventNameContainingAndEventStartBeforeOrderByEventEnd(String eventName, LocalDateTime dateNow) {
        return eventRepository.findAllByEventNameContainingAndEventStartBeforeOrderByEventEnd(eventName, dateNow);
    }

    //all events containing
    public List<Event> findAllByEventNameContainingOrderByEventEnd(String eventName) {
        return eventRepository.findAllByEventNameContainingOrderByEventEnd(eventName);
    }

    //future events
    //    public List<Event> findByEventStartAfterOrderByEventEnd(LocalDateTime dateNow) {
    //        return eventRepository.findByEventStartAfterOrderByEventEnd(dateNow);
    //    }

    //all events
    public List<Event> findAllByOrderByEventEnd() {
        return eventRepository.findAllByOrderByEventEnd();
    }

    @Transactional
    public void addEvent(EventForm eventForm) {
        final Event myEvent = Event.builder()
                .eventName(eventForm.getEventName())
                .eventStart(eventForm.getEventStart())
                .eventEnd(eventForm.getEventEnd())
                .eventDescription(eventForm.getEventDescription())
                .eventOrganiser(userRepository.findByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail()).get())
                .comments(Collections.emptyList())
                .build();

        eventRepository.save(myEvent);
    }


}
