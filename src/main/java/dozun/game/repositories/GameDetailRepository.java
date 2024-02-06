package dozun.game.repositories;

import dozun.game.entities.GameDetailEntity;
import dozun.game.entities.GameEntity;
import dozun.game.entities.UserEntity;
import dozun.game.enums.BetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameDetailRepository extends JpaRepository<GameDetailEntity, Long> {
    @Query("SELECT gde FROM GameDetailEntity gde JOIN GameEntity ge ON ge.id = gde.game.id " +
            "WHERE gde.user =:user AND gde.game =:game AND ge.status = true")
    Optional<GameDetailEntity> findByUserAndGame(@Param("user") UserEntity user,
                                                 @Param("game") GameEntity game);

    @Query("SELECT SUM(gde.betAmount) FROM GameDetailEntity gde JOIN GameEntity ge ON ge.id = gde.game.id " +
            "WHERE gde.user =:user AND gde.game =:game AND ge.status = true AND gde.betType = :betType")
    Double getSumMaxByUserAndGame(@Param("user") UserEntity user,
                                  @Param("game") GameEntity game,
                                  @Param("betType") BetType betType);

    @Query("SELECT SUM(gde.betAmount) FROM GameDetailEntity gde JOIN GameEntity ge ON ge.id = gde.game.id " +
            "WHERE gde.user =:user AND gde.game =:game AND ge.status = true AND gde.betType = :betType")
    Double getSumMinByUserAndGame(@Param("user") UserEntity user,
                                  @Param("game") GameEntity game,
                                  @Param("betType") BetType betType);

    @Query("SELECT SUM(gde.betAmount) FROM GameDetailEntity gde JOIN GameEntity ge ON ge.id = gde.game.id " +
            "WHERE gde.user =:user AND gde.game =:game AND ge.status = true AND gde.betType = :betType")
    Double getSumMaxByAllUserAndGame(@Param("game") GameEntity game,
                                     @Param("betType") BetType betType);

    @Query("SELECT SUM(gde.betAmount) FROM GameDetailEntity gde JOIN GameEntity ge ON ge.id = gde.game.id " +
            "WHERE gde.user =:user AND gde.game =:game AND ge.status = true AND gde.betType = :betType")
    Double getSumMinByAllUserAndGame(@Param("game") GameEntity game,
                                     @Param("betType") BetType betType);

}
