package dozun.game.repositories;

import dozun.game.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String role);
    @Query("SELECT r FROM RoleEntity r WHERE r.name = ?1")
    RoleEntity findRoleByName(String role);
}