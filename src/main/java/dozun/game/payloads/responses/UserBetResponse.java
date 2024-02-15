package dozun.game.payloads.responses;

import dozun.game.entities.WalletEntity;
import dozun.game.enums.GameResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBetResponse {
    private Double wallet;
    private Double max;
    private Double min;
    private String gameResult;
    private Double amount;
}
