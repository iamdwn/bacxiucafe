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
    Optional<GameEntity> findById(Long gameId);

    Optional<GameEntity> findFirstByStatusIsTrueOrderByGameStartDesc();
//    @Query("SELECT ge FROM  GameEntity ge WHERE ge.status = true OR " +
//            "( ge.status = false AND ge.countdown > 0)" +
//            " ORDER BY ge.gameStart DESC LIMIT 1")
//    Optional<GameEntity> findFirstByStatusOrderByGameStartDesc();

    @Query("SELECT ge FROM  GameEntity ge ORDER BY ge.gameStart DESC LIMIT 1")
    Optional<GameEntity> findFirstByStatusOrderByGameStartDesc();

    Optional<GameEntity> findByStatusIsTrue();

//    @Modifying
//    @Transactional
//    @Query("DELETE FROM GameEntity ge WHERE ge.countdown > :countdown")
//    void deleteByCountdownGreaterThan(int countdown);


//    @Query("select g from GameEntity g where g.gameStatus =  order by g.gameStart desc")
//    @QueryHints(value = @QueryHint(name = "org.hibernate.fetchSize", value="1"))
//    Optional<GameEntity> findFirstActiveGameOrderByStartDateDesc(GameStatus gameStatus);
}
