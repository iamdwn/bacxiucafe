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

//    @GetMapping("/getAllUser")
//    public List<UserEntity> getAllUser() {
//        return userService. getAllUser();
//    }

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
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        UserEntity user = new UserEntity(signUpRequest.getFullName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                true,
                true
        );

        Set<RoleEntity> roleEntities = new HashSet<>();
        RoleEntity userRole = roleRepository.findRoleByName("USER");
        roleEntities.add(userRole);
        user.setRoles(roleEntities);

        userRepository.save(user);
        walletRepository.save(new WalletEntity(
                user, 0D
        ));
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(signUpRequest.getUsername(), signUpRequest.getPassword());
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

//    @PostMapping("google")
//    public ResponseEntity<?> loginGoogle(@Valid @RequestBody SignupRequest signUpRequest) {
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        Optional<UserEntity> o_user = userRepository.findByUsernameAndStatusTrue(signUpRequest.getEmail());
//        if (o_user.isPresent()) {
//            String encodedPasswordFromDatabase = o_user.get().getPassword();
//            if (!passwordEncoder.matches(signUpRequest.getPassword(), encodedPasswordFromDatabase)) {
//                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username or password is wrong!"));
//            } else {
//                AuthenticationRequest authenticationRequest = new AuthenticationRequest(signUpRequest.getEmail(), signUpRequest.getPassword());
//                return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
//            }
//        }
//        UserEntity user = new UserEntity(signUpRequest.getFullName(),
//                signUpRequest.getUsername(),
//                signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()),
//                true,
//                true
//        );
//        Set<RoleEntity> roleEntities = new HashSet<>();
//        RoleEntity userRole = roleRepository.findByName("USER");
//        roleEntities.add(userRole);
//        user.setRoles(roleEntities);
//        userRepository.save(user);
//        AuthenticationRequest authenticationRequest = new AuthenticationRequest(signUpRequest.getEmail(), signUpRequest.getPassword());
//        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
//    }

//    @GetMapping("/getUserInfo")
//    public UserDTO InfoUser(@RequestHeader("Authorization") String token) {
//
//        String username = jwtService.extractTokenToGetUser(token.substring(7));
//        List<String> roles = jwtService.extractTokenToGetRoles(token.substring(7));
//        Optional<UserEntity> user = userRepository.findByUsernameAndStatusTrue(username);
//        UserDTO userDTO = new UserDTO();
//        userDTO.setFullName(user.get().getFullName());
//        userDTO.setEmail(user.get().getEmail());
//        userDTO.setId(user.get().getId());
//        userDTO.setPassword(user.get().getPassword());
//        userDTO.setRoles(roles);
//        userDTO.setUsername(user.get().getUsername());
//        return userDTO;
//
//    }

//    @GetMapping("/refreshToken")
//    public ResponseEntity<?> getNewToken(@RequestHeader("Authorization") String refreshToken) {
//        String username = jwtService.extractTokenToGetUser(refreshToken.substring(7));
//        if (username != null) {
//            UserEntity user = userRepository.findByUsernameAndStatusTrue(username).orElseThrow();
//            List<RoleEntity> role = null;
//            if (user != null) {
//                role = roleCustomRepo.getRole(user);
//            }
//            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            Set<RoleEntity> set = new HashSet<>();
//            role.stream().forEach(c -> set.add(new RoleEntity(c.getName())));
//            user.setRoles(set);
//            set.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));
//            var jwtToken = jwtService.generateToken(user, authorities);
//            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//            authenticationResponse.setToken(jwtToken);
//            authenticationResponse.setRefreshToken(refreshToken.substring(7));
//            return ResponseEntity.ok(authenticationResponse);
//        }
//        return ResponseEntity.badRequest().body(new MessageResponse("Can not have new token!!!"));
//
//    }
//   @GetMapping("/logout")
//public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
//       String token = authorizationHeader.substring(7);
//    // Lấy token từ yêu cầu HTTP
//    String token = request.getHeader("Authorization");
//
//    // Xóa token khỏi session
//    SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
//    SecurityContextHolder.getContext().setAuthentication(null);
//    SecurityContextHolder.getContext().getAuthentication().invalidate();
//
//    // Truyền thông với người dùng rằng họ đã đăng xuất
//    return ResponseEntity.ok("Đăng xuất thành công!");
//}
//
//}
}

