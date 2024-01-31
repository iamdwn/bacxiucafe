package dozun.game.repositories;

import dozun.game.entities.GameEntity;
import dozun.game.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    Optional<GameEntity> findById(Long gameId);
}
