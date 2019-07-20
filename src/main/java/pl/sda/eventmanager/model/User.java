package pl.sda.eventmanager.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "eventOrganiser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Event> organisedEvents;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_EVENT",
            joinColumns = {@JoinColumn(name = "EVENT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID", nullable = false)})
    private Set<Event> attendedEvents;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        final List<SimpleGrantedAuthority> ORGANISER_AUTHORITY_LIST
                = Collections.unmodifiableList(Arrays.asList(
                        (new SimpleGrantedAuthority(Role.ROLE_USER.name())),
                        (new SimpleGrantedAuthority(Role.ROLE_ORGANISER.name()))));

        final List<SimpleGrantedAuthority> ADMIN_AUTHORITY_LIST
                = Collections.unmodifiableList(Arrays.asList(
                        (new SimpleGrantedAuthority(Role.ROLE_USER.name())),
                        (new SimpleGrantedAuthority(Role.ROLE_ORGANISER.name())),
                        (new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()))));


        if (role == Role.ROLE_ORGANISER) {
            return ORGANISER_AUTHORITY_LIST;
        } else if (role == Role.ROLE_ADMIN) {
            return ADMIN_AUTHORITY_LIST;
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

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

}