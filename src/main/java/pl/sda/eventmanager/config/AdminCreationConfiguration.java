package pl.sda.eventmanager.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import pl.sda.eventmanager.dto.EventForm;
import pl.sda.eventmanager.dto.RegisterForm;
import pl.sda.eventmanager.model.Role;
import pl.sda.eventmanager.model.User;
import pl.sda.eventmanager.services.EventService;
import pl.sda.eventmanager.services.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Slf4j
public class AdminCreationConfiguration  {

    private final UserService userService;
    private final EventService eventService;

    public AdminCreationConfiguration(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        if (!userService.findByEmail("admin").isPresent()) {
            RegisterForm adminForm = new RegisterForm();

            // SET ADMIN LOGIN
            adminForm.setEmail("admin");
            // SET ADMIN NICKNAME (SHOWN TO OTHER USERS)
            adminForm.setNickname("ADMIN");
            // SET ADMIN PASSWORD
            String password = UUID.randomUUID().toString();
            adminForm.setPassword(password);
            adminForm.setRole(Role.ROLE_ADMIN);
            userService.saveUser(adminForm);
            log.info("=====================================================");
            log.info("Admin account created.");
            log.info("Admin password: " + password);
            log.info("=====================================================");


            // TODO FOR TESTING PURPOSES
            RegisterForm userForm = new RegisterForm();
            userForm.setEmail("user@user.user");
            userForm.setNickname("User");
            userForm.setPassword("useruser");
            userForm.setRole(Role.ROLE_USER);
            userService.saveUser(userForm);
            log.info("=====================================================");
            log.info("User: " + userForm.getNickname() + " created.");
            log.info("=====================================================");

            RegisterForm organiserForm = new RegisterForm();
            organiserForm.setEmail("organiser@organiser.organiser");
            organiserForm.setNickname("Organiser");
            organiserForm.setPassword("organiser");
            organiserForm.setRole(Role.ROLE_ORGANISER);
            userService.saveUser(organiserForm);
            log.info("=====================================================");
            log.info("User: " + organiserForm.getNickname() + " created.");
            log.info("=====================================================");

            EventForm eventForm = new EventForm();
            eventForm.setEventOrganiser(User.builder().nickname("TESTING").build());
            eventForm.setEventName("Test event");
            eventForm.setEventStart(LocalDateTime.now().minusDays(1));
            eventForm.setEventEnd(LocalDateTime.now().plusYears(1));
            eventForm.setEventDescription("TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST ");
            SecurityContextHolder.getContext().setAuthentication(new PreAuthenticatedAuthenticationToken(userService.findByNickname("Organiser").get(), null ));
            eventService.addEvent(eventForm);
            log.info("=====================================================");
            log.info("Event: " + eventForm.getEventName() + " created.");
            log.info("=====================================================");
        }

    }
}


