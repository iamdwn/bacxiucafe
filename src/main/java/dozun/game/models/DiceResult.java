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
    private Integer dice1;
    private Integer dice2;
    private Integer dice3;
}
