package dozun.game.services;

import dozun.game.constants.BetType;
import dozun.game.entities.GameEntity;
import dozun.game.repositories.GameRepository;
import dozun.game.threading.models.TaskResult;
import dozun.game.utils.GameGenerator;
import dozun.game.utils.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GameService {

    private GameGenerator gameGenerator;
    private RandomNumberGenerator randomNumberGenerator;
    private TaskExecutor taskExecutor;
    private BetType betType;
    private GameRepository gameRepository;


    @Autowired
    public GameService(GameGenerator gameGenerator, RandomNumberGenerator randomNumberGenerator, TaskExecutor taskExecutor, GameRepository gameRepository) {
        this.gameGenerator = gameGenerator;
        this.randomNumberGenerator = randomNumberGenerator;
        this.taskExecutor = taskExecutor;
        this.gameRepository = gameRepository;
    }

    public TaskResult generateGame(){
        return gameGenerator.getGame();
    }

    public void start() {

//        LocalDateTime now = LocalDateTime.now();
//        Long startTime = System.currentTimeMillis();
//        Long endTime = System.currentTimeMillis();
//        Long elapsedTime = endTime - startTime;
//
//        RandomNumberGenerator generator = new RandomNumberGenerator();
//
//        RandomTask dice1 = new RandomTask(generator);
//        RandomTask dice2 = new RandomTask(generator);
//        RandomTask dice3 = new RandomTask(generator);
//
//        Thread thread1 = new Thread(dice1);
//        Thread thread2 = new Thread(dice2);
//        Thread thread3 = new Thread(dice3);
//
//        thread1.start();
//        thread2.start();
//        thread3.start();

//        Long total = dice1.getResult() +
//                dice2.getResult() +
//                dice3.getResult();

//        betType = (total > 3 && total < 11)
//                ? BetType.XIU
//                : (total > 10 && total < 18)
//                ? BetType.TAI
//                : BetType.LOSE;

//        GameEntity gameEntity = new GameEntity(
//                dice1.getResult(),
//                dice2.getResult(),
//                dice3.getResult(),
//                betType,
//                now,
//                30L
//        );
//        gameRepository.save(gameEntity);
//
//        while ((elapsedTime/1000) <= 30) {
//            endTime = System.currentTimeMillis();
//            elapsedTime = endTime - startTime;
//        }

//        gameRepository.delete(gameEntity);
        throw new RuntimeException("game is ended");
    }
}
