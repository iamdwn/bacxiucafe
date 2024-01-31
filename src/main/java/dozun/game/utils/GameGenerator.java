package dozun.game.utils;

import dozun.game.models.DiceResult;
import org.springframework.stereotype.Component;

@Component
public class GameGenerator {
    private DiceResult diceResult;

    public DiceResult getGame(){
        int dice1 = RandomNumberGenerator.getDice1();
        int dice2 = RandomNumberGenerator.getDice2();
        int dice3 = RandomNumberGenerator.getDice3();

        return new DiceResult(
                dice1,
                dice2,
                dice3
        );
    }

}
