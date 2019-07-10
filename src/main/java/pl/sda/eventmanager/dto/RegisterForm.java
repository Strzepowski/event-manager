package pl.sda.eventmanager.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {

    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 3, max = 50)
    private String username;
    @NotNull
    @Size(min = 8, max = 30)
    private String password;
    @NotNull
    @Size(min = 8, max = 30)
    private String confirmPassword;
}
