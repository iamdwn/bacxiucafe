package dozun.game.controllers;

import dozun.game.model.ResponseObject;
import dozun.game.utils.TokenChecker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greet")
public class TestController {

    @GetMapping
    public ResponseEntity<ResponseObject> test(@RequestHeader("Authorization") String token){
        try {
            if (TokenChecker.checkToken(token)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "found", "greeting"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", "not found", ""));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", ex.getMessage(), ""));
        }
    }
}

