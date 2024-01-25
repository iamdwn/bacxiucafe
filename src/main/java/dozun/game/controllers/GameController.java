package dozun.game.controllers;

import dozun.game.constants.ResponseStatus;
import dozun.game.models.ResponseObject;
import dozun.game.services.GameService;
import dozun.game.utils.TokenChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/game")
public class GameController {
    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<ResponseObject> start(@RequestHeader("Authorization") String token){
        try {
            if (TokenChecker.checkToken(token)) {
                gameService.start();
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(dozun.game.constants.ResponseStatus.SUCCESS, "game is starting", ""));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(dozun.game.constants.ResponseStatus.BAD_REQUEST, "failed", "bet failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(ResponseStatus.SUCCESS, ex.getMessage(), ""));
        }
    }

    @GetMapping("/generate")
    public ResponseEntity<ResponseObject> generate(@RequestHeader("Authorization") String token){
        try {
            if (TokenChecker.checkToken(token)) {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(dozun.game.constants.ResponseStatus.SUCCESS, "game is starting", gameService.generateGame()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(dozun.game.constants.ResponseStatus.BAD_REQUEST, "failed", "bet failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(ResponseStatus.SUCCESS, ex.getMessage(), ""));
        }
    }


}
