package dozun.game.dtos;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String jwt;

}
