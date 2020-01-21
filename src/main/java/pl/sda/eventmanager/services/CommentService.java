package pl.sda.eventmanager.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.sda.eventmanager.dto.CommentForm;
import pl.sda.eventmanager.model.Comment;
import pl.sda.eventmanager.model.Event;
import pl.sda.eventmanager.model.User;
import pl.sda.eventmanager.repositories.CommentRepository;
import pl.sda.eventmanager.repositories.EventRepository;
import pl.sda.eventmanager.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    //all comments for event order by post datetime desc
    public List<Comment> findAllByCommentedEventOrderByPostDateTimeDesc(Event event){
        return commentRepository.findAllByCommentedEventOrderByPostDateTimeDesc(event);
    };

        public void addComment(CommentForm commentForm){
        final Comment newComment = Comment.builder()
                .commentAuthor(userRepository.findByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail()).get())
                .commentContents(commentForm.getCommentContents())
                .postDateTime(LocalDateTime.now())
                .commentedEvent(commentForm.getCommentedEvent())
                .build();

        commentRepository.save(newComment);

    }

}
