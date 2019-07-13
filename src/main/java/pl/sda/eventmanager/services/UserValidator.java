package pl.sda.eventmanager.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.sda.eventmanager.dto.LoginForm;
import pl.sda.eventmanager.dto.RegisterForm;
import pl.sda.eventmanager.model.User;

@Service
public class UserValidator implements Validator {

    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;

    }


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegisterForm registerForm = (RegisterForm) o;

        User user = User.UserBuilder
                .anUser()
                .withEmail(registerForm.getEmail())
                .withNickname(registerForm.getNickname())
                .withPassword(registerForm.getPassword())
                .withConfirmPassword(registerForm.getConfirmPassword())
                .build();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty", "All fields are required.");
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.registerForm.email", "Email already in base. Please Create account on another email.");
        }
        if (!user.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            errors.rejectValue("email", "NotEmail", "Please use proper email address.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", "NotEmpty", "All fields are required.");
        if (userService.findByNickname(user.getNickname()) != null) {
            errors.rejectValue("nickname", "Duplicate.registerForm.nickname", "Username taken. Please choose another nickname.");
        }
        if (user.getNickname().length() < 3 || user.getNickname().length() > 50) {
            errors.rejectValue("nickname", "Size.registerForm.nickname", "Please use between 3 and 50 characters.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "All fields are required.");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 30) {
            errors.rejectValue("password", "Size.registerForm.password", "Please use between 8 and 30 characters.");
        }
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Diff.registerForm.confirmPassword", "Passwords don't match.");
        }


    }

    public void validateLogin(Object o, Errors errors) {
        LoginForm loginForm = (LoginForm) o;

        User user = User.UserBuilder
                .anUser()
                .withEmail(loginForm.getEmail())
                .withPassword(loginForm.getPassword())
                .build();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty", "All fields are required.");
        if (!user.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            errors.rejectValue("email", "NotEmail", "Please use proper email address.");
        }
        if (userService.findByEmail(user.getEmail()) == null) {
            errors.rejectValue("email", "WrongEmailOrPassword", "Wrong email or password.");
        } else {

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "All fields are required.");
            if (!userService.findByEmail(loginForm.getEmail()).getPassword().equals(loginForm.getPassword())) {
                errors.rejectValue("password", "WrongEmailOrPassword", "Wrong email or password.");
            }

        }
    }

}