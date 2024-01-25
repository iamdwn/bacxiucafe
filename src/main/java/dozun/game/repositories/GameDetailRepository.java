package dozun.game.repositories;

import dozun.game.entities.GameDetailEntity;
import dozun.game.entities.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameDetailRepository extends JpaRepository<GameDetailEntity, Long> {
}
