package dozun.game.payloads.responses;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String jwt;

}
