package pl.sda.eventmanager.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "eventOrganiser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Event> organisedEvents = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_EVENT",
            joinColumns = {@JoinColumn(name = "EVENT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID", nullable = false)})
    private Set<Event> attendedEvents = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @Override
    public String toString() {
        return nickname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> organiserAuthorityList = new ArrayList<>();
        organiserAuthorityList.add(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
        organiserAuthorityList.add(new SimpleGrantedAuthority(Role.ROLE_ORGANISER.name()));

        List<SimpleGrantedAuthority> adminAuthorityList = new ArrayList<>();
        adminAuthorityList.add(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
        adminAuthorityList.add(new SimpleGrantedAuthority(Role.ROLE_ORGANISER.name()));
        adminAuthorityList.add(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()));

        if (role == Role.ROLE_ORGANISER) {
            return organiserAuthorityList;
        } else if (role == Role.ROLE_ADMIN) {
            return adminAuthorityList;
        }
        return Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public static final class UserBuilder {
        private Long id;
        private String email;
        private String nickname;
        private String password;
        private Role role;
        private Set<Event> organisedEvents = new HashSet<>();
        private UserInfo userInfo;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withRole(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder withOrganisedEvents(Set<Event> organisedEvents) {
            this.organisedEvents = organisedEvents;
            return this;
        }

        public UserBuilder withUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setNickname(nickname);
            user.setPassword(password);
            user.setRole(role);
            user.setOrganisedEvents(organisedEvents);
            user.setUserInfo(userInfo);
            return user;
        }
    }
}