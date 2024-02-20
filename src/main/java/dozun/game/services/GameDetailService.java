package dozun.game.services;

import dozun.game.entities.WalletEntity;
import dozun.game.enums.BetType;
import dozun.game.enums.GameStatus;
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

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class GameDetailService {
    private UserRepository userRepository;
    private GameRepository gameRepository;
    private GameDetailRepository gameDetailRepository;
    private WalletRepository walletRepository;
    private GameService gameService;
    private Double result = 0D;
    private ScheduledExecutorService scheduledExecutor;

    @Autowired
    public GameDetailService(UserRepository userRepository, GameRepository gameRepository, GameDetailRepository gameDetailRepository, WalletRepository walletRepository, GameService gameService, ScheduledExecutorService scheduledExecutor) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gameDetailRepository = gameDetailRepository;
        this.walletRepository = walletRepository;
        this.gameService = gameService;
        this.scheduledExecutor = scheduledExecutor;
    }

    public BetResponse bet(String username, BetRequest betDTO) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatusTrue(username);
        Optional<GameEntity> gameEntity = gameRepository.findFirstByGameStartDesc();
        Optional<WalletEntity> walletEntity = walletRepository.findByUser(userEntity.get());

        if (!userEntity.isPresent()
                || !(userEntity.get() != null))
            throw new RuntimeException("The specified user does not exist");

        if (!gameEntity.isPresent()
                || !(gameService.getCurrentSecond() >= 10))
            throw new RuntimeException("The current game bet is locked");

        if (!walletEntity.isPresent())
            throw new RuntimeException("Your balance is insufficient to place this bet");

        if (walletEntity.get().getBaseBalance() <= 0
                || (walletEntity.get().getBaseBalance() > 0
                && (walletEntity.get().getBaseBalance().compareTo(betDTO.getBetAmount())) == -1))
            throw new RuntimeException("Your balance is insufficient to place this bet");

        BetType betType = betDTO.getBetType().equalsIgnoreCase(BetType.TAI.name())
                ? BetType.TAI
                : BetType.XIU;

        walletEntity.get().setBaseBalance(walletEntity.get().getBaseBalance() - betDTO.getBetAmount());
        walletEntity.get().setBalance(walletEntity.get().getBalance() - betDTO.getBetAmount());
        walletRepository.save(walletEntity.get());

        GameResult gameResult = checkWin(betType, gameEntity.get());
        if (gameResult.equals(GameResult.WIN)) {
            walletEntity.get().setBalance(walletEntity.get().getBalance() + betDTO.getBetAmount() * 2);
            walletRepository.save(walletEntity.get());
        }

        GameDetailEntity gameDetailEntity = new GameDetailEntity(
                userEntity.get(),
                gameEntity.get(),
                betDTO.getBetAmount(),
                betType,
                gameResult,
                true
        );
        gameDetailRepository.save(gameDetailEntity);

        return new BetResponse(
                gameDetailEntity.getUser().getUsername(),
                gameDetailEntity.getBetAmount(),
                betDTO.getBetType().toUpperCase(),
                walletEntity.get().getBaseBalance()
        );
    }

    public GameResult checkWin(BetType betType, GameEntity gameEntity) {
        return betType.equals(gameEntity.getType())
                ? GameResult.WIN
                : GameResult.LOSE;
    }
}
