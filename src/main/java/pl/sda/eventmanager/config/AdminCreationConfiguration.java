package pl.sda.eventmanager.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.sda.eventmanager.dto.RegisterForm;
import pl.sda.eventmanager.model.Role;
import pl.sda.eventmanager.services.UserService;

@Component
@Slf4j
public class AdminCreationConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;

    public AdminCreationConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!userService.findByEmail("admin").isPresent()) {
            RegisterForm adminForm = new RegisterForm();
            adminForm.setEmail("admin");
            adminForm.setNickname("ADMIN");
            adminForm.setPassword("admin");
            adminForm.setRole(Role.ADMIN);
            userService.saveUser(adminForm);
            log.info("Admin account created.");
        }
    }
}


