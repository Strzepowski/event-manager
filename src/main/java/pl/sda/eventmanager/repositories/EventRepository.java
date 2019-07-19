package pl.sda.eventmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventmanager.model.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByEventName(String eventName);
    List<Event> findAll();

    //all sorting related to objects stored in DB should be done using DB (for ex. replace with findBy..OrderByEventEndDesc)
    List<Event> findAllBy


}
