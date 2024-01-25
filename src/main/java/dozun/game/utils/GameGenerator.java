package dozun.game.utils;

import dozun.game.threading.models.TaskResult;
import org.springframework.stereotype.Component;

@Component
public class GameGenerator {
    private TaskResult taskResult;

    public TaskResult getGame(){
        Long dice1 = RandomNumberGenerator.getDice1();
        Long dice2 = RandomNumberGenerator.getDice2();
        Long dice3 = RandomNumberGenerator.getDice3();

        return new TaskResult(
                dice1,
                dice2,
                dice3
        );
    }

}
