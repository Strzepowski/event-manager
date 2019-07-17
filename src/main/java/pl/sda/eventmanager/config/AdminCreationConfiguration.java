package pl.sda.eventmanager.config;

import lombok.extern.slf4j.Slf4j;
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

            //TODO IS THIS GOOD PLACE TO SET ADMIN LOGIN INFO?

            // SET ADMIN LOGIN
            adminForm.setEmail("admin");
            // SET ADMIN NICKNAME (SHOWN TO OTHER USERS)
            adminForm.setNickname("ADMIN");
            // SET ADMIN PASSWORD
            adminForm.setPassword("admin");
            adminForm.setRole(Role.ROLE_ADMIN);
            userService.saveUser(adminForm);
            log.info("Admin account created.");


            // TODO FOR TESTING PURPOSES
            RegisterForm userForm = new RegisterForm();
            userForm.setEmail("user@user.user");
            userForm.setNickname("User");
            userForm.setPassword("useruser");
            userForm.setRole(Role.ROLE_USER);
            userService.saveUser(userForm);
            RegisterForm organiserForm = new RegisterForm();
            organiserForm.setEmail("organiser@organiser.organiser");
            organiserForm.setNickname("Organiser");
            organiserForm.setPassword("organiser");
            organiserForm.setRole(Role.ROLE_ORGANISER);
            userService.saveUser(organiserForm);
        }
    }
}


