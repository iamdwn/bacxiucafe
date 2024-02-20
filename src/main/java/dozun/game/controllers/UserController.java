package dozun.game.controllers;

import dozun.game.entities.CustomUserDetails;
import dozun.game.enums.ResponseStatus;
import dozun.game.payloads.dtos.UserDTO;
import dozun.game.models.ResponseObject;
import dozun.game.services.JwtService;
import dozun.game.services.UserService;
import dozun.game.utils.TokenChecker;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin
@Tag(name = "User")
//@Api(tags="User")
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getCurrentUser(@RequestHeader("bearerAuth") String token) {
        try {
            token = "Bearer " + token;
            if (TokenChecker.checkToken(token)) {
                String username = jwtService.extractTokenToGetUser(token);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(dozun.game.enums.ResponseStatus.SUCCESS, "Success", userService.getCurrentUser(username)));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(dozun.game.enums.ResponseStatus.BAD_REQUEST, "Not found", "Failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), ""));
        }
    }
}
