package pl.sda.eventmanager.dto;

import lombok.Data;

@Data
public class RegisterForm {

    private String email;
    private String username;
    private String password;
    private String confirmPassword;
}
