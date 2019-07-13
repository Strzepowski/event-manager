package pl.sda.eventmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventmanager.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByNickname(String nickname);
    User findByEmail(String email);
}
