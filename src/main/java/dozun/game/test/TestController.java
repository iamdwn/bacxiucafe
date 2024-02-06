package dozun.game.test;

import dozun.game.enums.ResponseStatus;
import dozun.game.models.ResponseObject;
import dozun.game.utils.TokenChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    private TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }


    @GetMapping("/greet")
    public ResponseEntity<ResponseObject> test(@RequestHeader("Authorization") String token){
        try {
            if (TokenChecker.checkToken(token)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(ResponseStatus.SUCCESS, "found", "greeting"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, "not found", ""));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), ""));
        }
    }

//    @GetMapping("/random")
//    public Long random(){
//        return testService.getRandom();
//    }

}

