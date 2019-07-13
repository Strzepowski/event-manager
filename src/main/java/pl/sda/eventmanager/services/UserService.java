package pl.sda.eventmanager.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.eventmanager.model.Role;
import pl.sda.eventmanager.model.User;
import pl.sda.eventmanager.repositories.RoleRepository;
import pl.sda.eventmanager.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " doesn't exist");
        }

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }


        return new org.springframework.security.core.userdetails.User(user.getNickname(),
                user.getPassword(), grantedAuthorities);
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void save(String email, String nickname, String password, String confirmPassword) {

        final User myUser = new User();
        myUser.setEmail(email);
        myUser.setNickname(nickname);
        myUser.setPassword(passwordEncoder.encode(password));
        myUser.setConfirmPassword(confirmPassword);
        myUser.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(myUser);

    }

}


