package dozun.game.utils;

import dozun.game.models.DiceResult;
import dozun.game.payloads.responses.CountdownResponse;
import org.springframework.stereotype.Component;

@Component
public class GameGenerator {

    public DiceResult getGame(){
        int dice1 = ScheduleExecutor.getDice1();
        int dice2 = ScheduleExecutor.getDice2();
        int dice3 = ScheduleExecutor.getDice3();

        return new DiceResult(
                dice1,
                dice2,
                dice3
        );
    }
}
