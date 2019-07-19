package pl.sda.eventmanager.services;

import org.springframework.stereotype.Service;
import pl.sda.eventmanager.model.User;
import pl.sda.eventmanager.repositories.UserRepository;

import java.util.List;

@Service
public class AdminOperationsService {

    private final UserRepository userRepository;

    public AdminOperationsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



}
