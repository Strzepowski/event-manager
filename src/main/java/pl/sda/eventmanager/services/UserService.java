package pl.sda.eventmanager.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.eventmanager.model.User;
import pl.sda.eventmanager.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " doesn't exist");
        }
        return user;
    }

    @Transactional
    public void save(String username, String password, String email) {

        if (userRepository.findByEmail(email) == null) {
            final User myUser = new User();
            myUser.setUsername(username);
            myUser.setPassword(passwordEncoder.encode(password));
            myUser.setEmail(email);
            userRepository.save(myUser);
        }
    }

}


