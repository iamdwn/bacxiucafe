package dozun.game.controllers;

import dozun.game.entities.WalletEntity;
import dozun.game.repositories.WalletRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import dozun.game.auth.AuthenticationResponse;
import dozun.game.auth.AuthenticationRequest;
import dozun.game.auth.MessageResponse;
import dozun.game.auth.SignupRequest;
import dozun.game.payloads.dtos.UserDTO;
import dozun.game.entities.RoleEntity;
import dozun.game.entities.UserEntity;
import dozun.game.repositories.RoleCustomRepository;
import dozun.game.repositories.RoleRepository;
import dozun.game.repositories.UserRepository;
import dozun.game.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import dozun.game.services.JwtService;
import dozun.game.services.UserService;

import java.util.*;


@RestController
@RequiredArgsConstructor
//@CrossOrigin
@Tag(name = "Auth")
//@Api(tags="Auth")
@RequestMapping("api/v1/auth")
//@CrossOrigin(origins = {"http://localhost:8080"})
//@CrossOrigin(origins = "*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @Autowired
    RoleCustomRepository roleCustomRepo;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Optional<UserEntity> o_user = userRepository.findByUsernameAndStatusTrue(authenticationRequest.getUsername());
        if (o_user.isPresent()) {
            String encodedPasswordFromDatabase = o_user.get().getPassword();
            if (!passwordEncoder.matches(authenticationRequest.getPassword(), encodedPasswordFromDatabase)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username or password is wrong!"));
            } else {
                return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
            }
        } else return ResponseEntity.badRequest().body(new MessageResponse("Error: Username or password is wrong!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: The username is already in use"));
        }

        if (userRepository.existsByEmail(signUpRequest.getUsername() + "@gmail.com")) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: The email is already registered"));
        }

        UserEntity user = new UserEntity(
                signUpRequest.getUsername(),
                signUpRequest.getUsername() + "@gmail.com",
                encoder.encode(signUpRequest.getPassword()),
                new Date(),
                true,
                true
        );

        Set<RoleEntity> roleEntities = new HashSet<>();
        RoleEntity userRole = roleRepository.findRoleByName("USER");
        roleEntities.add(userRole);
        user.setRoles(roleEntities);

        userRepository.save(user);
        walletRepository.save(new WalletEntity(
                user, 0D, 0D
        ));

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(signUpRequest.getUsername(), signUpRequest.getPassword());
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}

