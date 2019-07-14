package pl.sda.eventmanager.dto;

import lombok.Data;
import pl.sda.eventmanager.model.Role;

@Data
public class RegisterForm {

    private String email;
    private String nickname;
    private String password;
    private String confirmPassword;
    private Role role = Role.ROLE_USER;
}
