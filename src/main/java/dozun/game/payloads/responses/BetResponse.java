package dozun.game.payloads.responses;

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

    private GameResult gameResult;

    private Double amountMaxOfUser;

    private Double amountMinOfUser;

//    private Double amountMaxOfAll;
//
//    private Double amountMinOfAll;
}
