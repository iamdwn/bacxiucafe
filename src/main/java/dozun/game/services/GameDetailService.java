package dozun.game.services;

import dozun.game.constants.BetType;
import dozun.game.dtos.BetDTO;
import dozun.game.entities.GameDetailEntity;
import dozun.game.entities.GameEntity;
import dozun.game.entities.UserEntity;
import dozun.game.repositories.GameDetailRepository;
import dozun.game.repositories.GameRepository;
import dozun.game.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameDetailService {
    private UserRepository userRepository;
    private GameRepository gameRepository;
    private GameDetailRepository gameDetailRepository;

    @Autowired
    public GameDetailService(UserRepository userRepository, GameRepository gameRepository, GameDetailRepository gameDetailRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gameDetailRepository = gameDetailRepository;
    }

    public GameDetailEntity bet(BetDTO betDTO) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatusTrue(betDTO.getUsername());
        Optional<GameEntity> gameEntity = gameRepository.findById(betDTO.getGameId());

        if (!userEntity.isPresent())
            throw new RuntimeException("this user is not exist");

        if (!gameEntity.isPresent())
            throw new RuntimeException("this game is ended");

        if (userEntity.get().getWallet().getBalance() < betDTO.getBetAmount())
            throw new RuntimeException("your balance is not enough for this bet");

        BetType betType = betDTO.getBetType().equals(BetType.TAI.name())
                ? BetType.TAI
                : BetType.XIU;

        GameDetailEntity gameDetailEntity = new GameDetailEntity(
                userEntity.get(),
                gameEntity.get(),
                betDTO.getBetAmount(),
                betType
        );

        return gameDetailRepository.save(gameDetailEntity);
    }
}
