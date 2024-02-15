package dozun.game.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.json.JSONArray;
import org.json.JSONObject;
import dozun.game.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private static final String Secret_key = "123";

    public String generateToken(UserEntity user, Collection<SimpleGrantedAuthority> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(Secret_key.getBytes());
        return JWT.create()
//                .withSubject(user.getUsername())
//                .withClaim("id", user.getId())
                .withClaim("user", user.getUsername())
//                .withClaim("fullname",user.getFullName())
//                .withClaim("email",user.getEmail())
//                .withClaim("picture", user.getPicture())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String generateRefreshToken(UserEntity user, Collection<SimpleGrantedAuthority> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(Secret_key.getBytes());
        return JWT.create()
                .withClaim("user", user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30L * 3600 * 24 * 1000))
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String extractTokenToGetUser(String token) {
        String[] parts = token.split("\\.");
//        JSONObject header = new JSONObject(decode(parts[0]));
        JSONObject payload = new JSONObject(decode(parts[1]));
//        String signature = decode(parts[2]);
        boolean exp = payload.getLong("exp") > (System.currentTimeMillis() / 1000);
        if (exp) {
            return payload.getString("user");
        }
        return null;
    }

    public List<String> extractTokenToGetRoles(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid Token format");
        }
//        JSONObject header = new JSONObject(decode(parts[0]));
        JSONObject payload = new JSONObject(decode(parts[1]));
        String signature = parts[2];
        String headerAndPayloadHashed = hmacSha256(parts[0] + "." + parts[1], Secret_key);
        boolean exp = payload.getLong("exp") > (System.currentTimeMillis() / 1000);
        if (exp && signature.equals(headerAndPayloadHashed)) {
            JSONArray rolesArray = payload.getJSONArray("roles");
            List<String> roles = new ArrayList<>();
            for (int i = 0; i < rolesArray.length(); i++) {
                roles.add(rolesArray.getString(i));
            }
            return roles;
        }
        return null;
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    private static String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hmacSha256(String data, String secret) {
        try {

            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return encode(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            return null;
        }
    }
}
