package dozun.game.controllers;

import dozun.game.dtos.UserDTO;
import dozun.game.model.ResponseObject;
import dozun.game.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseObject> signup(@RequestBody UserDTO user) {
        return userService.signup(user);
    }
}
