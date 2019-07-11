package pl.sda.eventmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventmanager.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
