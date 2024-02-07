package dozun.game.services;

import dozun.game.enums.BetType;
import dozun.game.enums.Duration;
import dozun.game.entities.GameEntity;
import dozun.game.payloads.responses.GameResponse;
import dozun.game.repositories.GameDetailRepository;
import dozun.game.repositories.GameRepository;
import dozun.game.models.DiceResult;
import dozun.game.utils.GameGenerator;
import dozun.game.utils.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameService {

    private GameGenerator gameGenerator;
    private RandomNumberGenerator randomNumberGenerator;
    private BetType betType;
    private GameRepository gameRepository;
    private Duration duration;
    private final SimpMessagingTemplate messagingTemplate;
    private GameDetailRepository gameDetailRepository;
    private ScheduledExecutorService scheduler;
    private ScheduledExecutorService scheLockBet;
    private AtomicInteger counter;

    @Autowired
    public GameService(GameGenerator gameGenerator, RandomNumberGenerator randomNumberGenerator, GameRepository gameRepository, AtomicInteger counter, SimpMessagingTemplate messagingTemplate, GameDetailRepository gameDetailRepository, ScheduledExecutorService scheduler, ScheduledExecutorService scheLockBet) {
        this.gameGenerator = gameGenerator;
        this.randomNumberGenerator = randomNumberGenerator;
        this.gameRepository = gameRepository;
        this.messagingTemplate = messagingTemplate;
        this.gameDetailRepository = gameDetailRepository;
        this.scheduler = scheduler;
        this.scheLockBet = scheLockBet;
        this.counter = new AtomicInteger(0);
    }


//    public DiceResult start() {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                int currentCount = counter.incrementAndGet();
//                DiceResult diceResult = gameGenerator.getGame();
//                BetType gameType = checkGameType(diceResult);
//                GameEntity gameEntity = new GameEntity(
//                        diceResult.getDice1(),
//                        diceResult.getDice2(),
//                        diceResult.getDice3(),
//                        gameType,
//                        new Date(),
//                        Duration.valueOf("GAME_DURATION").getDur(),
//                        true
//                );
//                gameRepository.save(gameEntity);
//        }, 0, Duration.valueOf("GAME_DURATION").getDur() * 1000L);
//}

    public void start() {
        scheduler.scheduleAtFixedRate(this::generate, 0, 5, TimeUnit.SECONDS);
    }

    private void generate() {
        Double sumMaxOfAll = 0D;
        Double sumMinOfAll = 0D;
        DiceResult diceResult = gameGenerator.getGame();
        BetType gameType = checkGameType(diceResult);
        GameEntity gameEntity = new GameEntity(
                diceResult.getDice1(),
                diceResult.getDice2(),
                diceResult.getDice3(),
                gameType,
                new Date(),
                Duration.valueOf("GAME_DURATION").getDur(),
                true
        );
        scheLockBet.schedule(() -> {
            lockBet(gameEntity);
        }, 3, TimeUnit.SECONDS);
//        scheLockBet.shutdown();
        gameRepository.save(gameEntity);
        if (!gameDetailRepository.findAllByGame(gameEntity).isEmpty()
                || !(gameDetailRepository.findAllByGame(gameEntity) == null)) {
            sumMaxOfAll = gameDetailRepository.getSumMaxByAllUserAndGame(gameEntity, BetType.TAI);
            sumMinOfAll = gameDetailRepository.getSumMinByAllUserAndGame(gameEntity, BetType.XIU);
        }
        messagingTemplate.convertAndSend("/topic/game",
                new GameResponse(
                        diceResult,
                        sumMaxOfAll,
                        sumMinOfAll
                ));
    }

    private void lockBet(GameEntity gameEntity) {
        gameEntity.setStatus(false);
        gameRepository.save(gameEntity);
    }

    public BetType checkGameType(DiceResult diceResult) {
        int total;
        if (diceResult.getDice1() == diceResult.getDice2()
                && diceResult.getDice1() == diceResult.getDice3())
            return BetType.TAMBAO;

        total = diceResult.getDice1() + diceResult.getDice2() + diceResult.getDice3();

        if (total == 3 || total == 18) return BetType.TAMBAO;

        if (total < 11) return BetType.XIU;
        return BetType.TAI;
    }
}
