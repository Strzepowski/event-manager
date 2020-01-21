package pl.sda.eventmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventmanager.model.Comment;
import pl.sda.eventmanager.model.Event;

import java.util.List;
import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

//all comments for event order by post datetime desc
List<Comment> findAllByCommentedEventOrderByPostDateTimeDesc(Event event);


}
