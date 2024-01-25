package dozun.game.threading.tasks;

import dozun.game.threading.models.TaskResult;
import dozun.game.utils.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiThreadTask implements Runnable {
    private RandomNumberGenerator rng;
    private TaskResult taskResult;

    @Autowired
    public MultiThreadTask(RandomNumberGenerator rng, TaskResult taskResult) {
        this.rng = rng;
        this.taskResult = taskResult;
    }

    @Override
    public void run() {
        Long dice1 = RandomNumberGenerator.getDice1();
        Long dice2 = RandomNumberGenerator.getDice2();
        Long dice3 = RandomNumberGenerator.getDice3();

        taskResult.setDice1(dice1);
        taskResult.setDice1(dice2);
        taskResult.setDice1(dice3);
    }

}