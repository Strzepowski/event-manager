package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.dto.CommentForm;
import pl.sda.eventmanager.services.CommentService;
import pl.sda.eventmanager.services.EventService;
import pl.sda.eventmanager.services.validation.CommentValidator;

@Controller
public class EventController {

    private final EventService eventService;
    private final CommentService commentService;
    private final CommentValidator commentValidator;

    public EventController(EventService eventService, CommentService commentService, CommentValidator commentValidator) {
        this.eventService = eventService;
        this.commentService = commentService;
        this.commentValidator = commentValidator;
    }

    @GetMapping("event")
    public ModelAndView getEvent(@RequestParam(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("event", "commentForm", new CommentForm());
        modelAndView.addObject("singleEvent", eventService.findById(id).get());
        modelAndView.addObject("comments", commentService.findAllByCommentedEventOrderByPostDateTimeDesc(eventService.findById(id).get()));
        modelAndView.addObject("commentCount", commentService.findAllByCommentedEventOrderByPostDateTimeDesc(eventService.findById(id).get()).size());
        return modelAndView;
    }

    @PostMapping("event")
    public ModelAndView postEvent(@RequestParam(value = "id") Long id, @ModelAttribute CommentForm commentForm, BindingResult bindingResult){
        commentValidator.validate(commentForm, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("event", "commentForm", new CommentForm());
            modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
            modelAndView.addObject("singleEvent", eventService.findById(id).get());
            modelAndView.addObject("comments", commentService.findAllByCommentedEventOrderByPostDateTimeDesc(eventService.findById(id).get()));
            modelAndView.addObject("commentCount", commentService.findAllByCommentedEventOrderByPostDateTimeDesc(eventService.findById(id).get()).size());
            return modelAndView;
        }

        commentForm.setCommentedEvent(eventService.findById(id).get());
        commentService.addComment(commentForm);

        ModelAndView modelAndView = new ModelAndView("event", "commentForm", new CommentForm());
        modelAndView.addObject("singleEvent", eventService.findById(id).get());
        modelAndView.addObject("comments", commentService.findAllByCommentedEventOrderByPostDateTimeDesc(eventService.findById(id).get()));
        modelAndView.addObject("commentCount", commentService.findAllByCommentedEventOrderByPostDateTimeDesc(eventService.findById(id).get()).size());
        return modelAndView;

    }
}
