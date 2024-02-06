package dozun.game.payloads.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {
    private String username;
    private Double chargeAmount;
}
