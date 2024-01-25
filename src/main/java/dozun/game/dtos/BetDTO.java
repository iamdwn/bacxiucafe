package dozun.game.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetDTO {
    private String username;
    private Long gameId;
    private Double betAmount;
    private String betType;
}
