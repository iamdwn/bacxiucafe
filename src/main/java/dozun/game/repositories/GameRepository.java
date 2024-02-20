package dozun.game.repositories;

import dozun.game.entities.GameEntity;
import dozun.game.entities.UserEntity;
import dozun.game.enums.GameStatus;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    @Query("SELECT ge FROM GameEntity ge WHERE ge.status = true ORDER BY ge.gameStart DESC LIMIT 1")
    Optional<GameEntity> findFirstByGameStartDesc();

    @Query("SELECT ge FROM GameEntity ge ORDER BY ge.gameStart DESC LIMIT 1")
    Optional<GameEntity> findFirstOrderByGameStartDesc();
}
