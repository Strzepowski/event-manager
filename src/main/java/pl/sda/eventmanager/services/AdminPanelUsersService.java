package pl.sda.eventmanager.services;

import org.springframework.stereotype.Service;
import pl.sda.eventmanager.model.User;
import pl.sda.eventmanager.repositories.UserRepository;

import java.util.List;

@Service
public class AdminPanelUsersService {

    private final UserRepository userRepository;

    public AdminPanelUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        List<User> allByOrderById = userRepository.findAllByOrderById();
        allByOrderById.remove(0);
        return allByOrderById;
    }

}
