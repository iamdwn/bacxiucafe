package dozun.game.utils;

import dozun.game.entities.GameEntity;
import dozun.game.enums.Duration;
import dozun.game.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ScheduleExecutor {

    private static int dice1;
    private static int dice2;
    private static int dice3;
    private Random random = new Random();

    @Scheduled(fixedRate = 36000)
    public void generateDice1() {
        dice1 = random.nextInt(6) + 1;
    }

    @Scheduled(fixedRate = 36000)
    public void generateDice2() {
        dice2 = random.nextInt(6) + 1;
    }

    @Scheduled(fixedRate = 36000)
    public void generateDice3() {
        dice3 = random.nextInt(6) + 1;
    }

    public static int getDice1() {
        return dice1;
    }

    public static int getDice2() {
        return dice2;
    }

    public static int getDice3() {
        return dice3;
    }
}