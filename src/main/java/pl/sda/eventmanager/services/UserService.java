package pl.sda.eventmanager.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.eventmanager.model.Role;
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
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        return user;
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void save(String email, String nickname, String password, String confirmPassword, Role role) {

        final User myUser = new User();
        myUser.setEmail(email);
        myUser.setNickname(nickname);
        myUser.setPassword(passwordEncoder.encode(password));
        myUser.setConfirmPassword(confirmPassword);
        myUser.setRole(role);
        userRepository.save(myUser);

    }

}


