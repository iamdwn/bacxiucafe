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

    @Autowired
    public GameDetailService(UserRepository userRepository, GameRepository gameRepository, GameDetailRepository gameDetailRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gameDetailRepository = gameDetailRepository;
        this.walletRepository = walletRepository;
    }

    public BetResponse bet(BetRequest betDTO, Boolean isAllIn) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatusTrue(betDTO.getUsername());
        Optional<GameEntity> gameEntity = gameRepository.findFirstByStatusIsTrueOrderByGameStartDesc();
        Optional<WalletEntity> walletEntity = walletRepository.findByUser(userEntity.get());

        if (!userEntity.isPresent()
                || !(userEntity.get() != null))
            throw new RuntimeException("this user is not exist");

        if (!gameEntity.isPresent())
            throw new RuntimeException("this bet of game is locked");

        if (!walletEntity.isPresent())
            throw new RuntimeException("your balance is not enough for this be");

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
                !isAllIn ? betDTO.getBetAmount() : walletEntity.get().getBalance(),
                betType,
                gameResult
        );

        if (gameResult.equals(GameResult.WIN))
            walletEntity.get().setBalance(!isAllIn ? walletEntity.get().getBalance() + betDTO.getBetAmount()
                    : walletEntity.get().getBalance() * 2);
        else {
            walletEntity.get().setBalance(!isAllIn ? walletEntity.get().getBalance() - betDTO.getBetAmount() : 0);
        }

        walletRepository.save(walletEntity.get());

        gameDetailRepository.save(gameDetailEntity);

        Double sumMaxOfUser = gameDetailRepository.getSumMaxByUserAndGame(userEntity.get(), gameEntity.get(), BetType.TAI);
        Double sumMinOfUser = gameDetailRepository.getSumMinByUserAndGame(userEntity.get(), gameEntity.get(), BetType.XIU);
//        Double sumMaxOfAll = gameDetailRepository.getSumMaxByAllUserAndGame(gameEntity.get(), BetType.TAI);
//        Double sumMinOfAll = gameDetailRepository.getSumMinByAllUserAndGame(gameEntity.get(), BetType.XIU);

        return new BetResponse(
                gameDetailEntity.getUser().getUsername(),
                gameDetailEntity.getBetAmount(),
                gameDetailEntity.getBetType().name(),
                gameDetailEntity.getGameResult(),
                sumMaxOfUser,
                sumMinOfUser
//                sumMaxOfAll,
//                sumMinOfAll
        );
    }

    public GameResult checkWin(BetType betType, GameEntity gameEntity) {
        return betType.equals(gameEntity.getType())
                ? GameResult.WIN
                : GameResult.LOSE;
    }
}
