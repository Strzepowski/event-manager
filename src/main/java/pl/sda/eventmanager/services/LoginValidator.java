package pl.sda.eventmanager.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.sda.eventmanager.dto.LoginForm;

@Service
public class LoginValidator implements Validator {

    private final UserService userService;

    public LoginValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return LoginForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        LoginForm loginForm = (LoginForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty", "All fields are required.");
        if (!loginForm.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            errors.rejectValue("email", "NotEmail", "Please use proper email address.");
        }
        if (!userService.findByEmail(loginForm.getEmail()).isPresent()) {
            errors.rejectValue("email", "WrongEmailOrPassword", "Wrong email or password.");
        } else {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "All fields are required.");
            if (!userService.findByEmail(loginForm.getEmail()).get().getPassword().equals(loginForm.getPassword())) {
                errors.rejectValue("password", "WrongEmailOrPassword", "Wrong email or password.");
            }
        }
    }
}
