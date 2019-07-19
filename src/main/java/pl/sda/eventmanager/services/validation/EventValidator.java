package pl.sda.eventmanager.services.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.sda.eventmanager.dto.EventForm;
import pl.sda.eventmanager.services.EventService;

import java.time.LocalDateTime;

@Service
public class EventValidator implements Validator {

    private final EventService eventService;

    public EventValidator(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return EventForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EventForm eventForm = (EventForm) o;

        if (eventForm.getEventStart() == null || eventForm.getEventEnd() == null) {
            errors.rejectValue("eventStart", "NotEmpty", "All fields are required.");
        } else {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventName", "NotEmpty", "All fields are required.");
            if (eventService.findByEventName(eventForm.getEventName()).isPresent()) {
                errors.rejectValue("eventName", "Duplicate.eventForm.eventName", "Event name already in our database. Please choose a different event name.");
            }
            if (eventForm.getEventName().length() < 3) {
                errors.rejectValue("eventName", "Size.eventForm.eventName", "Please use minimum 3 characters.");
            }
            if (eventForm.getEventEnd().isBefore(LocalDateTime.now())) {
                errors.rejectValue("eventEnd", "Date.eventForm.eventEnd", "Event already finished. Check event ending time.");
            }
            if (eventForm.getEventStart().isAfter(eventForm.getEventEnd())) {
                errors.rejectValue("eventStart", "Date.eventForm.eventStart", "Event cannot begin after it ended. Check event start and endig time.");
            }
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventDescription", "NotEmpty", "All fields are required.");
            if (eventForm.getEventDescription().length() < 20) {
                errors.rejectValue("eventDescription", "Size.eventForm.eventDescription", "Please use minimum 20 characters.");
            }
        }
    }
}
