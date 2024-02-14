package dozun.game.payloads.responses;

import dozun.game.entities.WalletEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBetResponse {
    private Double wallet;
    private Double amountOfMax;
    private Double amountOfMin;
}
