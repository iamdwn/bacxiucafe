package dozun.game.controllers;

import dozun.game.entities.GameEntity;
import dozun.game.enums.ResponseStatus;
import dozun.game.models.ResponseObject;
import dozun.game.repositories.GameRepository;
import dozun.game.services.GameService;
import dozun.game.utils.TokenChecker;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@Tag(name = "Board")
//@Api(tags="Board")
@RequestMapping("api/v1/game")
public class GameController {
    private GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;
    private GameRepository gameRepository;

    @Autowired
    public GameController(GameService gameService, GameRepository gameRepository, SimpMessagingTemplate messagingTemplate) {
        this.gameService = gameService;
        this.messagingTemplate = messagingTemplate;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/start")
    public ResponseEntity<ResponseObject> generate(@RequestHeader("Authorization") String token) {
        try {
            if (TokenChecker.checkToken(token)) {
                gameService.start();
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(dozun.game.enums.ResponseStatus.SUCCESS, "game is starting", ""));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(dozun.game.enums.ResponseStatus.BAD_REQUEST, "failed", "bet failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(ResponseStatus.SUCCESS, ex.getMessage(), ""));
        }
    }
}
