package pl.sda.eventmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventmanager.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    //single event by name
    Optional<Event> findByEventName(String eventName);

    //single event by id
    Optional<Event> findById(Long id);

    //future events
    //List<Event> findByEventStartAfterOrderByEventEnd(LocalDateTime dateNow);

    //future events containing
    List<Event> findAllByEventNameContainingAndEventStartAfterOrderByEventEnd(String eventName, LocalDateTime dateNow);

    //ongoing events containing
    List<Event> findAllByEventNameContainingAndEventStartBeforeOrderByEventEnd(String eventName, LocalDateTime dateNow);

    //all events containing
    List<Event> findAllByEventNameContainingOrderByEventEnd(String eventName);

    //all events
    List<Event> findAllByOrderByEventEnd();

}
