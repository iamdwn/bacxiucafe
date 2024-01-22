package dozun.game.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dozun.game.services.JwtService;

import java.util.List;

@Component
public class TokenChecker {
    private static JwtService jwtService = null;

    @Autowired
    public TokenChecker(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public static boolean checkToken(String token) {
        List<String> roles = jwtService.extractTokenToGetRoles(token.substring(7));
        if (roles != null) {
            if (!roles.isEmpty()) {
                return true;
            }
            throw new RuntimeException("Role is null!!");
        }
        throw new RuntimeException("token is wrong or role is not sp!!");
    }
}
