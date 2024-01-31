package dozun.game.services;

import dozun.game.auth.AuthenticationResponse;
import dozun.game.auth.AuthenticationRequest;
import dozun.game.entities.RoleEntity;
import dozun.game.entities.UserEntity;
import dozun.game.repositories.RoleCustomRepository;
import dozun.game.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleCustomRepository roleCustomRepo;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        UserEntity user = userRepository.findByUsernameAndStatusTrue(authenticationRequest.getUsername()).orElseThrow();
        String fullname = user.getFullName();
        String password = user.getPassword();
        String email = user.getEmail();
        UUID id = user.getId();
        List<RoleEntity> role = null;
        if(user != null){
            role = roleCustomRepo.getRole(user);
        }


        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Set<RoleEntity> setRole = new HashSet<>();
        role.stream().forEach(r -> setRole.add(new RoleEntity(r.getName())));
        user.setRoles(setRole);
        setRole.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));
        var jwtToken = jwtService.generateToken(user, authorities);
        var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);

        return AuthenticationResponse.builder().token(jwtToken).refreshToken(jwtRefreshToken).build();
    }
}