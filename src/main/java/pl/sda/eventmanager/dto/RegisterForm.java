package pl.sda.eventmanager.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {

    private String email;
    private String username;
    private String password;
    private String confirmPassword;
}
