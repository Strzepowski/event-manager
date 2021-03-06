package pl.sda.eventmanager.services.validation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.sda.eventmanager.dto.LoginForm;
import pl.sda.eventmanager.services.UserService;

@Service
public class AdminValidator implements Validator {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminValidator(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return LoginForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        LoginForm loginForm = (LoginForm) o;


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "Please use proper email address.");
        if (!passwordEncoder.matches(loginForm.getPassword(), userService.findByEmail(loginForm.getEmail()).get().getPassword())) {
            errors.rejectValue("password", "WrongEmailOrPassword", "Please use proper email address.");
        }
    }
}

