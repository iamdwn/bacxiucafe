package dozun.game.controllers;

import dozun.game.enums.ResponseStatus;
import dozun.game.payloads.requests.BetRequest;
import dozun.game.models.ResponseObject;
import dozun.game.services.GameDetailService;
import dozun.game.utils.TokenChecker;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Tag(name = "GameDetail")
//@Api(tags="GameDetail")
@RequestMapping("api/v1/game")
@SecurityRequirement(name = "bearerAuth")
public class GameDetailController {
    private GameDetailService gameDetailService;

    @Autowired
    public GameDetailController(GameDetailService gameDetailService) {
        this.gameDetailService = gameDetailService;
    }

    @PostMapping("/bet")
    public ResponseEntity<ResponseObject> bet(HttpServletRequest request,
                                              @RequestBody BetRequest betDTO) {
        try {
            String token = request.getHeader("Authorization");
            if (TokenChecker.checkToken(token)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(ResponseStatus.SUCCESS, "success", gameDetailService.bet(betDTO, false)));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, "failed", "bet failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), ""));
        }
    }

    @PostMapping("/bet/all")
    public ResponseEntity<ResponseObject> betAll(HttpServletRequest request,
                                                 @RequestBody BetRequest betDTO) {
        try {
            String token = request.getHeader("Authorization");
            if (TokenChecker.checkToken(token)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(ResponseStatus.SUCCESS, "success", gameDetailService.bet(betDTO, true)));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, "failed", "bet failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), ""));
        }
    }
}
