package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
