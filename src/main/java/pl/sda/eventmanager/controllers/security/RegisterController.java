package pl.sda.eventmanager.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.controllers.IndexListController;
import pl.sda.eventmanager.dto.RegisterForm;
import pl.sda.eventmanager.services.EventService;
import pl.sda.eventmanager.services.UserService;
import pl.sda.eventmanager.services.validation.RegisterValidator;

@Controller
public class RegisterController extends IndexListController {

    private final UserService userService;
    private final RegisterValidator registerValidator;

    public RegisterController(UserService userService, RegisterValidator registerValidator, EventService eventService) {
        super(eventService);
        this.userService = userService;
        this.registerValidator = registerValidator;
    }

    @GetMapping("register")
    public ModelAndView registerGet() {
        return new ModelAndView("register", "registerForm", new RegisterForm());
    }

    @PostMapping("register")
    public ModelAndView registerPost(@ModelAttribute RegisterForm registerForm, BindingResult bindingResult) {

        registerValidator.validate(registerForm, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
            return modelAndView;
        }

        userService.saveUser(registerForm);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("accountCreated", registerForm.getEmail());
        modelAndView.addObject("events", getEventList());
        return modelAndView;
    }
}