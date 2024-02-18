package dozun.game.services;

import dozun.game.entities.*;
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


    public UserEntity saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public RoleEntity saveRole(RoleEntity role) {
        return null;
    }

    public void addToUser(String username, String rolename) {
        UserEntity user = userRepository.findByUsernameAndStatusTrue(username).get();
        RoleEntity role = roleRepository.findByName(rolename);
        user.getRoles().add(role);
    }

    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    public ResponseEntity<ResponseObject> signup(UserDTO user) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatusTrue(user.getUsername());
        if (userEntity.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(ResponseStatus.BAD_REQUEST, "this username is exist", ""));

        userEntity.get().setUsername(user.getUsername());
        userEntity.get().setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity.get());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(ResponseStatus.SUCCESS, "signup successfully", ""));
    }

    public UserBetResponse getCurrentUser(String username) {
        Double sumMax = 0D;
        Double sumMin = 0D;
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatusTrue(username);
        if (!userEntity.isPresent()) return null;

        Optional<WalletEntity> walletEntity = walletRepository.findByUser(userEntity.get());
        Optional<GameEntity> gameEntity = gameRepository.findFirstOrderByGameStartDesc();

        if (!walletEntity.isPresent()) return null;

        if (!gameDetailRepository.findAllByGame(gameEntity.get()).isEmpty()
                && !(gameDetailRepository.findAllByGame(gameEntity.get()) == null)) {
            sumMax = (gameDetailRepository.getByUserAndGame(userEntity.get(), gameEntity.get(), BetType.TAI)) > 0
                    ? gameDetailRepository.getSumByUserAndGame(userEntity.get(), gameEntity.get(), BetType.TAI) : 0D;

            sumMin = (gameDetailRepository.getByUserAndGame(userEntity.get(), gameEntity.get(), BetType.XIU)) > 0
                    ? gameDetailRepository.getSumByUserAndGame(userEntity.get(), gameEntity.get(), BetType.XIU) : 0D;
        }

//        gameDetailService.exchange(userEntity.get(), gameEntity.get().getType(), walletEntity.get(), gameEntity.get());
        Double result = gameDetailService.getResult();


        return new UserBetResponse(
                walletEntity.get().getBalance(),
                sumMax,
                sumMin,
                (gameService.getCurrentSecond() > 0
                        && !gameEntity.get().getStatus())
                        || gameEntity.get().getStatus() ? null : gameEntity.get().getType().name(),
                result
        );
    }
}
