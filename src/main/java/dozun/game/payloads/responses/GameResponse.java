package dozun.game.payloads.responses;

import dozun.game.models.DiceResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
    private DiceResult diceResult;
    private Double amountMaxOfAll;
    private Double amountMinOfAll;
}
