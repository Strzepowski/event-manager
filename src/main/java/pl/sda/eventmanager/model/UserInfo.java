package pl.sda.eventmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class UserInfo {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String phone;
    private String location;

}
