package dozun.game.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNumberGenerator {

    private static Long dice1;
    private static Long dice2;
    private static Long dice3;
    private Random random = new Random();

    @Scheduled(fixedRate = 5000)
    public void generateDice1() {
        dice1 = random.nextLong(6) + 1;
    }

    @Scheduled(fixedRate = 5000)
    public void generateDice2() {
        dice2 = random.nextLong(6) + 1;
    }

    @Scheduled(fixedRate = 5000)
    public void generateDice3() {
        dice3 = random.nextLong(6) + 1;
    }

    public static Long getDice1() {
        return dice1;
    }
    public static Long getDice2() {
        return dice2;
    }

    public static Long getDice3() {
        return dice3;
    }


}