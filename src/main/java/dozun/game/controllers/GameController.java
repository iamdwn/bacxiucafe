package dozun.game.controllers;

import dozun.game.enums.ResponseStatus;
import dozun.game.models.ResponseObject;
import dozun.game.repositories.GameRepository;
import dozun.game.services.GameService;
import dozun.game.utils.TokenChecker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Tag(name = "Board")
//@Api(tags="Board")
@RequestMapping("api/v1/game")
@SecurityRequirement(name = "bearerAuth")
public class GameController {
    private GameService gameService;
    private GameRepository gameRepository;

    @Autowired
    public GameController(GameService gameService, GameRepository gameRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/start")
    public ResponseEntity<ResponseObject> generate(HttpServletRequest request) {

        try {
            String token = request.getHeader("Authorization");
            if (TokenChecker.checkToken(token)) {
                gameService.start();
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(dozun.game.enums.ResponseStatus.SUCCESS, "game is starting", ""));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(dozun.game.enums.ResponseStatus.BAD_REQUEST, "failed", "bet failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), ""));
        }
    }

    @GetMapping("/starting/countdown")
    public ResponseEntity<ResponseObject> getCountdown(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (TokenChecker.checkToken(token)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(dozun.game.enums.ResponseStatus.SUCCESS, "game is starting", gameService.getCurrentSecond()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(dozun.game.enums.ResponseStatus.BAD_REQUEST, "failed", "bet failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), ""));
        }
    }


    @Operation(description = "Get current game")
    @GetMapping("/starting")
    public ResponseEntity<ResponseObject> getCurrentGame(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (TokenChecker.checkToken(token)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(dozun.game.enums.ResponseStatus.SUCCESS, "success", gameService.getCurrentGame()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(dozun.game.enums.ResponseStatus.BAD_REQUEST, "failed", "bet failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), ""));
        }
    }
}
