package dozun.game.repositories;

import dozun.game.entities.UserEntity;
import dozun.game.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> { ;
    Optional<WalletEntity> findByUser(UserEntity user);
}
