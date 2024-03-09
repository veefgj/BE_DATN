package SD94.repository.role;

import SD94.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select * from roles where role_name = ?", nativeQuery = true)
    Role find(String role_name);
}

