package pl.sda.eventmanager.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User eventOrganiser;

    @Column(nullable = false, unique = true)
    private String eventName;
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventStart;
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventEnd;
    @Column(nullable = false)
    @Type(type = "text")
    private String eventDescription;

    @ManyToMany(mappedBy = "attendedEvents")
    private Set<User> eventAttendants;

    @OneToMany(mappedBy = "commentedEvent")
    private List<Comment> comments;
}