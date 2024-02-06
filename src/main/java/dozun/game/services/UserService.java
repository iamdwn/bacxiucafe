package dozun.game.services;

import dozun.game.enums.ResponseStatus;
import dozun.game.payloads.dtos.UserDTO;
import dozun.game.entities.RoleEntity;
import dozun.game.entities.UserEntity;
import dozun.game.hash.Hashing;
import dozun.game.models.ResponseObject;
import dozun.game.repositories.RoleRepository;
import dozun.game.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private Hashing hashing;
    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, Hashing hashing) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashing = hashing;
    }


    public UserEntity saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public RoleEntity saveRole(RoleEntity role) {
        return null;
    }

    public void addToUser(String username, String rolename) {
        UserEntity user = userRepository.findByUsernameAndStatusTrue(username).get();
        RoleEntity role = roleRepository.findByName(rolename);
        user.getRoles().add(role);
    }

    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    public ResponseEntity<ResponseObject> signup(UserDTO user) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatusTrue(user.getUsername());
        if (userEntity.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(ResponseStatus.BAD_REQUEST, "this username is exist", ""));

        userEntity.get().setUsername(user.getUsername());
        userEntity.get().setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity.get());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(ResponseStatus.SUCCESS, "signup successfully", ""));
    }
}
