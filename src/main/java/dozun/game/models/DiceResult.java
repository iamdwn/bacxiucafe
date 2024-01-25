package dozun.game.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class DiceResult {
    private Long dice1;
    private Long dice2;
    private Long dice3;
}
