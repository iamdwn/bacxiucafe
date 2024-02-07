package dozun.game.controllers;

import dozun.game.payloads.dtos.UserDTO;
import dozun.game.models.ResponseObject;
import dozun.game.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Tag(name = "User")
//@Api(tags="User")
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/signup")
//    public ResponseEntity<ResponseObject> signup(@Valid @RequestBody UserDTO user) {
//        return userService.signup(user);
//    }
}
