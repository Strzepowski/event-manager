package pl.sda.eventmanager.services.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.sda.eventmanager.dto.RegisterForm;
import pl.sda.eventmanager.services.UserService;

@Service
public class RegisterValidator implements Validator {

    private final UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RegisterForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegisterForm registerForm = (RegisterForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty", "All fields are required.");
        if (userService.findByEmail(registerForm.getEmail()).isPresent()) {
            errors.rejectValue("email", "Duplicate.registerForm.email", "Email already in base. Please Create account on another email.");
        }
        if (!registerForm.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            errors.rejectValue("email", "NotEmail", "Please use proper email address.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", "NotEmpty", "All fields are required.");
        if (userService.findByNickname(registerForm.getNickname()).isPresent()) {
            errors.rejectValue("nickname", "Duplicate.registerForm.nickname", "Nickname taken. Please choose another nickname.");
        }
        if (registerForm.getNickname().length() < 3 || registerForm.getNickname().length() > 50) {
            errors.rejectValue("nickname", "Size.registerForm.nickname", "Please use between 3 and 50 characters.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "All fields are required.");
        if (registerForm.getPassword().length() < 8 || registerForm.getPassword().length() > 30) {
            errors.rejectValue("password", "Size.registerForm.password", "Please use between 8 and 30 characters.");
        }
        if (!registerForm.getConfirmPassword().equals(registerForm.getPassword())) {
            errors.rejectValue("confirmPassword", "Diff.registerForm.confirmPassword", "Passwords don't match.");
        }
    }


}