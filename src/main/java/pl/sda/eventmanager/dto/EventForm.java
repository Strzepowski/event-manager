package pl.sda.eventmanager.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.sda.eventmanager.model.User;

import java.time.LocalDateTime;

@Data
public class EventForm {

    private String eventName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventStart;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventEnd;
    private String eventDescription;


}
