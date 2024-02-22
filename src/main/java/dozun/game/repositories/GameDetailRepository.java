package dozun.game.repositories;

import dozun.game.entities.GameDetailEntity;
import dozun.game.entities.GameEntity;
import dozun.game.entities.UserEntity;
import dozun.game.enums.BetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameDetailRepository extends JpaRepository<GameDetailEntity, Long> {
    @Query("SELECT SUM(gde.betAmount) FROM GameDetailEntity gde JOIN GameEntity ge ON ge.id = gde.game.id " +
            "WHERE gde.user =:user AND gde.game =:game AND gde.betType = :betType")
    Double getSumByUserAndGame(@Param("user") UserEntity user,
                               @Param("game") GameEntity game,
                               @Param("betType") BetType betType);

    @Query("SELECT COUNT(gde) FROM GameDetailEntity gde JOIN GameEntity ge ON ge.id = gde.game.id " +
            "WHERE gde.user =:user AND gde.game =:game AND gde.betType = :betType")
    Long getByUserAndGame(@Param("user") UserEntity user,
                          @Param("game") GameEntity game,
                          @Param("betType") BetType betType);

    @Query("SELECT COUNT(gde) FROM GameDetailEntity gde JOIN GameEntity ge ON ge.id = gde.game.id " +
            "WHERE gde.user =:user AND gde.game =:game")
    Long getByUser(@Param("user") UserEntity user,
                          @Param("game") GameEntity game);

    @Query("SELECT SUM(gde.betAmount) FROM GameDetailEntity gde JOIN GameEntity ge ON ge.id = gde.game.id " +
            "WHERE gde.game =:game AND gde.betType = :betType")
    Double getSumByAllUserAndGame(@Param("game") GameEntity game,
                                  @Param("betType") BetType betType);

    @Query("SELECT COUNT(gde) FROM GameDetailEntity gde JOIN GameEntity ge ON ge.id = gde.game.id " +
            "WHERE gde.game =:game AND gde.betType = :betType")
    Long getByAllUserAndGame(@Param("game") GameEntity game,
                             @Param("betType") BetType betType);

    @Query("SELECT gde FROM GameDetailEntity gde JOIN GameEntity ge ON ge.id = gde.game.id " +
            "WHERE gde.game =:game")
    List<GameDetailEntity> findAllByGame(@Param("game") GameEntity game);
}
