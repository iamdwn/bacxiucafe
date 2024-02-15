package dozun.game.services;

import dozun.game.entities.WalletEntity;
import dozun.game.enums.BetType;
import dozun.game.payloads.requests.BetRequest;
import dozun.game.entities.GameDetailEntity;
import dozun.game.entities.GameEntity;
import dozun.game.entities.UserEntity;
import dozun.game.enums.GameResult;
import dozun.game.payloads.responses.BetResponse;
import dozun.game.repositories.GameDetailRepository;
import dozun.game.repositories.GameRepository;
import dozun.game.repositories.UserRepository;
import dozun.game.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameDetailService {
    private UserRepository userRepository;
    private GameRepository gameRepository;
    private GameDetailRepository gameDetailRepository;
    private WalletRepository walletRepository;
    private GameService gameService;

    @Autowired
    public GameDetailService(UserRepository userRepository, GameRepository gameRepository, GameDetailRepository gameDetailRepository, WalletRepository walletRepository, GameService gameService) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gameDetailRepository = gameDetailRepository;
        this.walletRepository = walletRepository;
        this.gameService = gameService;
    }

    public BetResponse bet(String username, BetRequest betDTO) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatusTrue(username);
        Optional<GameEntity> gameEntity = gameRepository.findFirstByStatusIsTrueOrderByGameStartDesc();
        Optional<WalletEntity> walletEntity = walletRepository.findByUser(userEntity.get());

        if (!userEntity.isPresent()
                || !(userEntity.get() != null))
            throw new RuntimeException("this user is not exist");

        if (!gameEntity.isPresent())
            throw new RuntimeException("this bet of game is locked");

        if (!walletEntity.isPresent())
            throw new RuntimeException("your balance is not enough for this bet");

        if (walletEntity.get().getBalance() <= 0
                || (walletEntity.get().getBalance() > 0
                && (walletEntity.get().getBalance().compareTo(betDTO.getBetAmount())) == -1))
            throw new RuntimeException("your balance is not enough for this bet");

        BetType betType = (gameEntity.get().getDice1() == gameEntity.get().getDice2()
                && gameEntity.get().getDice1() == gameEntity.get().getDice3())
                ? BetType.TAMBAO
                : betDTO.getBetType().equals(BetType.TAI.name())
                ? BetType.TAI
                : BetType.XIU;

        GameResult gameResult = checkWin(betType, gameEntity.get());

        GameDetailEntity gameDetailEntity = new GameDetailEntity(
                userEntity.get(),
                gameEntity.get(),
                betDTO.getBetAmount(),
                betType,
                (gameService.getCurrentSecond() > 0
                        && !gameEntity.get().getStatus())
                        || gameEntity.get().getStatus() ? null : gameResult
        );

        walletEntity.get().setBalance(walletEntity.get().getBalance() - betDTO.getBetAmount());
        walletRepository.save(walletEntity.get());

        gameDetailRepository.save(gameDetailEntity);

        return new BetResponse(
                gameDetailEntity.getUser().getUsername(),
                gameDetailEntity.getBetAmount(),
                gameDetailEntity.getBetType().name(),
                gameDetailEntity.getGameResult()
        );
    }

    public GameResult checkWin(BetType betType, GameEntity gameEntity) {
        return betType.equals(gameEntity.getType())
                ? GameResult.WIN
                : GameResult.LOSE;
    }

    public void exchange(UserEntity userEntity, BetType gameType, WalletEntity walletEntity, GameEntity gameEntity) {
        if (userEntity != null
                && walletEntity != null
                && gameEntity != null) {

            Double result = gameType.equals(BetType.TAI)
                    ? gameDetailRepository.getSumMaxByUserAndGame(userEntity, gameEntity, BetType.TAI)
                    : gameDetailRepository.getSumMaxByUserAndGame(userEntity, gameEntity, BetType.XIU);

            walletEntity.setBalance(walletEntity.getBalance() + result * 2);

            walletRepository.save(walletEntity);
        }
    }
}
