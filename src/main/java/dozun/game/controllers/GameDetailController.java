package dozun.game.controllers;

import dozun.game.entities.UserEntity;
import dozun.game.entities.WalletEntity;
import dozun.game.enums.ResponseStatus;
import dozun.game.payloads.requests.BetAllRequest;
import dozun.game.payloads.requests.BetRequest;
import dozun.game.models.ResponseObject;
import dozun.game.repositories.UserRepository;
import dozun.game.repositories.WalletRepository;
import dozun.game.services.GameDetailService;
import dozun.game.services.JwtService;
import dozun.game.utils.TokenChecker;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@CrossOrigin
@Tag(name = "GameDetail")
//@Api(tags="GameDetail")
@RequestMapping("api/v1/game")
@SecurityRequirement(name = "bearerAuth")
public class GameDetailController {
    private GameDetailService gameDetailService;
    private JwtService jwtService;
    private WalletRepository walletRepository;
    private UserRepository userRepository;

    @Autowired
    public GameDetailController(GameDetailService gameDetailService, JwtService jwtService, WalletRepository walletRepository, UserRepository userRepository) {
        this.gameDetailService = gameDetailService;
        this.jwtService = jwtService;
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/bet")
    public ResponseEntity<ResponseObject> bet(@RequestHeader("bearerAuth") String token,
                                              @RequestBody BetRequest betDTO) {
        try {
            token = "Bearer " + token;
            String username = jwtService.extractTokenToGetUser(token);
            if (TokenChecker.checkToken(token)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(ResponseStatus.SUCCESS, "success", gameDetailService.bet(username, betDTO)));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, "failed", "bet failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), ""));
        }
    }

    @PostMapping("/bet/all")
    public ResponseEntity<ResponseObject> betAll(@RequestHeader("bearerAuth") String token,
                                                 @RequestBody BetAllRequest betDTO) {
        try {
            token = "Bearer " + token;
            String username = jwtService.extractTokenToGetUser(token);
            Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatusTrue(username);
            if (!userEntity.isPresent()) throw new RuntimeException("Not found user");
            Optional<WalletEntity> walletEntity = walletRepository.findByUser(userEntity.get());
            if (!userEntity.isPresent()) throw new RuntimeException("Not found wallet");

                    BetRequest betRequest = new BetRequest(
                    walletEntity.get().getBalance(),
                    betDTO.getBetType()
            );

            if (TokenChecker.checkToken(token)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(ResponseStatus.SUCCESS, "success", gameDetailService.bet(username, betRequest)));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, "failed", "bet failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), ""));
        }
    }
}
