package dozun.game.payloads.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import dozun.game.enums.GameResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetResponse {
    private String username;
    private Double betAmount;
    private String betType;
    private Double balance;
}
