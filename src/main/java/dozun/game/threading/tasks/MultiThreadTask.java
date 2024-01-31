package dozun.game.threading.tasks;

import dozun.game.models.DiceResult;
import dozun.game.utils.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiThreadTask implements Runnable {
    private RandomNumberGenerator rng;
    private DiceResult diceResult;

    @Autowired
    public MultiThreadTask(RandomNumberGenerator rng, DiceResult diceResult) {
        this.rng = rng;
        this.diceResult = diceResult;
    }

    @Override
    public void run() {
        int dice1 = RandomNumberGenerator.getDice1();
        int dice2 = RandomNumberGenerator.getDice2();
        int dice3 = RandomNumberGenerator.getDice3();

        diceResult.setDice1(dice1);
        diceResult.setDice1(dice2);
        diceResult.setDice1(dice3);
    }

}