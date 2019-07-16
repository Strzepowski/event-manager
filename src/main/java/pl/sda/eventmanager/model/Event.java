package pl.sda.eventmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long Id;

    @Column(nullable = false, unique = true)
    private String eventName;
    @Column(nullable = false)
    private LocalDateTime eventStart;
    @Column(nullable = false)
    private LocalDateTime eventEnd;
    @Column(nullable = false)
    private String eventDescription;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User eventOrganiser;

    @ManyToMany(mappedBy = "attendedEvents")
    private Set<User> eventAttendants;

    public static final class EventBuilder {
        private Long Id;
        private String eventName;
        private LocalDateTime eventStart;
        private LocalDateTime eventEnd;
        private String eventDescription;
        private User eventOrganiser;
        private Set<User> eventAttendants;

        private EventBuilder() {
        }

        public static EventBuilder anEvent() {
            return new EventBuilder();
        }

        public EventBuilder withId(Long Id) {
            this.Id = Id;
            return this;
        }

        public EventBuilder withEventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        public EventBuilder withEventStart(LocalDateTime eventStart) {
            this.eventStart = eventStart;
            return this;
        }

        public EventBuilder withEventEnd(LocalDateTime eventEnd) {
            this.eventEnd = eventEnd;
            return this;
        }

        public EventBuilder withEventDescription(String eventDescription) {
            this.eventDescription = eventDescription;
            return this;
        }

        public EventBuilder withEventOrganiser(User eventOrganiser) {
            this.eventOrganiser = eventOrganiser;
            return this;
        }

        public EventBuilder withEventAttendants(Set<User> eventAttendants) {
            this.eventAttendants = eventAttendants;
            return this;
        }

        public Event build() {
            Event event = new Event();
            event.setId(Id);
            event.setEventName(eventName);
            event.setEventStart(eventStart);
            event.setEventEnd(eventEnd);
            event.setEventDescription(eventDescription);
            event.setEventOrganiser(eventOrganiser);
            event.setEventAttendants(eventAttendants);
            return event;
        }
    }
}
