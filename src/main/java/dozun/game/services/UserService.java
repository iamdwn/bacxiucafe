package dozun.game.services;

import dozun.game.entities.*;
import dozun.game.enums.BetRate;
import dozun.game.enums.BetType;
import dozun.game.enums.GameResult;
import dozun.game.enums.ResponseStatus;
import dozun.game.payloads.dtos.UserDTO;
import dozun.game.hash.Hashing;
import dozun.game.models.ResponseObject;
import dozun.game.payloads.responses.UserBetResponse;
import dozun.game.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private GameService gameService;
    private GameDetailService gameDetailService;
    private GameDetailRepository gameDetailRepository;
    private GameRepository gameRepository;
    private WalletRepository walletRepository;
    private Hashing hashing;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, GameService gameService, GameDetailService gameDetailService, GameDetailRepository gameDetailRepository, GameRepository gameRepository, WalletRepository walletRepository, Hashing hashing) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.gameService = gameService;
        this.gameDetailService = gameDetailService;
        this.gameDetailRepository = gameDetailRepository;
        this.gameRepository = gameRepository;
        this.walletRepository = walletRepository;
        this.hashing = hashing;
    }

    public UserBetResponse getCurrentUser(String username) {
        Double sumMax = 0D;
        Double sumMin = 0D;
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatusTrue(username);
        if (!userEntity.isPresent()) return null;

        Optional<WalletEntity> walletEntity = walletRepository.findByUser(userEntity.get());
        Optional<GameEntity> gameEntity = gameRepository.findFirstOrderByGameStartDesc();

        if (!walletEntity.isPresent()) return null;
        if (!gameEntity.isPresent()) return null;

        if (!gameDetailRepository.findAllByGame(gameEntity.get()).isEmpty()
                && !(gameDetailRepository.findAllByGame(gameEntity.get()) == null)) {
            sumMax = (gameDetailRepository.getByUserAndGame(userEntity.get(), gameEntity.get(), BetType.TAI)) > 0
                    ? gameDetailRepository.getSumByUserAndGame(userEntity.get(), gameEntity.get(), BetType.TAI) : 0D;

            sumMin = (gameDetailRepository.getByUserAndGame(userEntity.get(), gameEntity.get(), BetType.XIU)) > 0
                    ? gameDetailRepository.getSumByUserAndGame(userEntity.get(), gameEntity.get(), BetType.XIU) : 0D;
        }

        if (!(gameService.getCurrentSecond() > 0)
                || !(gameDetailRepository.getByUser(userEntity.get(), gameEntity.get()) > 0)) {
            walletEntity.get().setBaseBalance(walletEntity.get().getBalance());
            walletRepository.save(walletEntity.get());

            if (!(walletEntity.get().getBalance() > 0)) {
                walletEntity.get().setBalance(100000D);
                walletEntity.get().setBaseBalance(walletEntity.get().getBalance());
                walletRepository.save(walletEntity.get());
            }
        }

        return new UserBetResponse(
                gameService.getCurrentSecond() > 0 ? walletEntity.get().getBaseBalance() : walletEntity.get().getBalance(),
                sumMax,
                sumMin,
                (gameService.getCurrentSecond() > 0
                        && !gameEntity.get().getStatus())
                        || gameEntity.get().getStatus() ? null : gameEntity.get().getType().name(),
                gameService.getCurrentSecond() >= 1
                        ? 0D : gameEntity.get().getType().equals(BetType.TAI)
                        ? sumMax * BetRate.RATE.getRate() : sumMin * BetRate.RATE.getRate(),
                gameService.getCurrentSecond() >= 1
                        ? 0D : gameEntity.get().getType().equals(BetType.TAI)
                        ? 0 - sumMin : 0 - sumMax
        );
    }
}
