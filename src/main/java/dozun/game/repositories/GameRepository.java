package dozun.game.repositories;

import dozun.game.entities.GameEntity;
import dozun.game.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<GameEntity, Long> {
    Optional<GameEntity> findById(Long gameId);
}
