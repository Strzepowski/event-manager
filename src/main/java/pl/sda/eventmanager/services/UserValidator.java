package pl.sda.eventmanager.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
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
                .withUsername(registerForm.getUsername())
                .withPassword(registerForm.getPassword())
                .withConfirmPassword(registerForm.getConfirmPassword())
                .build();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.registerForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.registerForm.username");
        }
        if (user.getUsername().length() <= 3 || user.getUsername().length() >= 50) {
            errors.rejectValue("username", "Size.registerForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 30) {
            errors.rejectValue("password", "Size.registerForm.password");
        }
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Diff.registerForm.confirmPassword");
        }
    }
}