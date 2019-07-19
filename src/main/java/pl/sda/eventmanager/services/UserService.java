package pl.sda.eventmanager.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.eventmanager.dto.RegisterForm;
import pl.sda.eventmanager.model.User;
import pl.sda.eventmanager.model.UserInfo;
import pl.sda.eventmanager.repositories.UserRepository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

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
        if (userRepository.findByEmail(email).isPresent()) {
            return userRepository.findByEmail(email).get();
        }

        throw new UsernameNotFoundException(email + " not found in database");
    }

    public List<User> findAll(){
        return userRepository.findAllByOrderById();
    }

    public Optional<User> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void saveUser(RegisterForm registerForm) {

        final User myUser = User.builder()
                .email(registerForm.getEmail())
                .nickname(registerForm.getNickname())
                .password(passwordEncoder.encode(registerForm.getPassword()))
                .role(registerForm.getRole())
                .userInfo(new UserInfo())
                .build();

        userRepository.save(myUser);
    }

}


