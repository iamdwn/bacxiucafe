package dozun.game.payloads.requests;

import dozun.game.enums.GameResult;
import io.micrometer.common.lang.Nullable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetRequest {
    private Double betAmount;
    private String betType;
}
