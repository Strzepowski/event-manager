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

    public static final class UserInfoBuilder {
        private Long id;
        private User user;
        private String name;
        private String surname;
        private LocalDate birthDate;
        private String phone;
        private String location;

        private UserInfoBuilder() {
        }

        public static UserInfoBuilder anUserInfo() {
            return new UserInfoBuilder();
        }

        public UserInfoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserInfoBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public UserInfoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserInfoBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserInfoBuilder withBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserInfoBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserInfoBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public UserInfo build() {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(id);
            userInfo.setUser(user);
            userInfo.setName(name);
            userInfo.setSurname(surname);
            userInfo.setBirthDate(birthDate);
            userInfo.setPhone(phone);
            userInfo.setLocation(location);
            return userInfo;
        }
    }
}
