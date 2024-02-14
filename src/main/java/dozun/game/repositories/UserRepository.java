package dozun.game.repositories;

import dozun.game.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByUsername(String username);

    Boolean existsByPassword(String password);

    Boolean existsByEmail(String email);
    Optional<UserEntity> findByUsernameAndStatusTrue(String username);

    Optional<UserEntity> findByUsername(String username);
}
