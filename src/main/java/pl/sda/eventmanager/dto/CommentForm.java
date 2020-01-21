package pl.sda.eventmanager.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.sda.eventmanager.model.Event;
import pl.sda.eventmanager.model.User;

import java.time.LocalDateTime;

@Data
public class CommentForm {

    private String commentContents;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime postDateTime;
    private User commentAuthor;
    private Event commentedEvent;
}
