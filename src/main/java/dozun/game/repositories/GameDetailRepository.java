package dozun.game.repositories;

import dozun.game.entities.GameDetailEntity;
import dozun.game.entities.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDetailRepository extends JpaRepository<GameDetailEntity, Long> {


}
