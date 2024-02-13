package dozun.game.services;

import dozun.game.enums.BetType;
import dozun.game.enums.Duration;
import dozun.game.entities.GameEntity;
import dozun.game.enums.GameStatus;
import dozun.game.payloads.responses.GameResponse;
import dozun.game.repositories.GameDetailRepository;
import dozun.game.repositories.GameRepository;
import dozun.game.models.DiceResult;
import dozun.game.utils.GameGenerator;
import dozun.game.utils.ScheduleExecutor;
//import dozun.game.websocket.WebSocketSession;
//import dozun.game.websocket.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Service
public class GameService {

    private GameGenerator gameGenerator;
    private ScheduleExecutor scheduleExecutor;

    private BetType betType;
    private GameRepository gameRepository;
    private Duration duration;
    private final SimpMessagingTemplate messagingTemplate;
    private GameDetailRepository gameDetailRepository;
    private ScheduledExecutorService scheduler;
    private ScheduledExecutorService scheduledExecutor;
    private Long second = Duration.valueOf("GAME_DURATION").getDur();

    @Autowired
    public GameService(GameGenerator gameGenerator, ScheduleExecutor scheduleExecutor, GameRepository gameRepository, SimpMessagingTemplate messagingTemplate, GameDetailRepository gameDetailRepository, ScheduledExecutorService scheduler, ScheduledExecutorService scheduledExecutor) {
        this.gameGenerator = gameGenerator;
        this.scheduleExecutor = scheduleExecutor;
        this.gameRepository = gameRepository;
        this.messagingTemplate = messagingTemplate;
        this.gameDetailRepository = gameDetailRepository;
        this.scheduler = scheduler;
        this.scheduledExecutor = scheduledExecutor;
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
//        scheduler.scheduleAtFixedRate(this::generate, 0, 33, TimeUnit.SECONDS);
//        scheduler.scheduleAtFixedRate(() -> generate(user), 0, 33, TimeUnit.SECONDS);
        generate();
    }

    private void generate() {
        scheduler.scheduleAtFixedRate(() -> {
            DiceResult diceResult = gameGenerator.getGame();
            BetType gameType = checkGameType(diceResult);
            GameEntity gameEntity = new GameEntity(
                    diceResult.getDice1(),
                    diceResult.getDice2(),
                    diceResult.getDice3(),
                    gameType,
                    new Date(),
                    Duration.valueOf("GAME_DURATION").getDur(),
                    true,
                    Duration.valueOf("GAME_DURATION").getDur()
            );
            scheduledExecutor.schedule(() -> {
                resetTime();
//            webSocketSession.onClose();
            }, 33, TimeUnit.SECONDS);

            scheduledExecutor.schedule(() -> {
                lockBet(gameEntity);
            }, Duration.valueOf("GAME_DURATION").getDur() - Duration.BET_LOCKED_DURATION.getDur(), TimeUnit.SECONDS);

            scheduledExecutor.scheduleAtFixedRate(() -> {
                countdown(gameEntity);
            }, 1, 1, TimeUnit.SECONDS);
            gameRepository.save(gameEntity);
        },0, 33, TimeUnit.SECONDS);

        scheduledExecutor.scheduleAtFixedRate(() -> {
            messagingTemplate.convertAndSend("/topic/game",
                    getCurrentGame());
        }, 1, 1, TimeUnit.SECONDS);

    }

    private void lockBet(GameEntity gameEntity) {
        gameEntity.setStatus(false);
        gameRepository.save(gameEntity);
    }

    private void countdown(GameEntity gameEntity) {
        if (gameEntity.getCountdown() > 0) {
            gameEntity.setCountdown(gameEntity.getCountdown() - 1);
            second = (second > 0) ? gameEntity.getCountdown() - 1 : 0;
            gameRepository.save(gameEntity);
        }
    }

    public void resetTime() {
        second = Duration.valueOf("GAME_DURATION").getDur();
    }

    public Long getCurrentSecond() {
        return second;
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

    public GameResponse getCurrentGame() {
        Double sumMaxOfAll = 0D;
        Double sumMinOfAll = 0D;
        Optional<GameEntity> gameEntity = gameRepository.findFirstByStatusOrderByGameStartDesc();
        if (!gameDetailRepository.findAllByGame(gameEntity.get()).isEmpty()
                && !(gameDetailRepository.findAllByGame(gameEntity.get()) == null)) {
            sumMaxOfAll = gameDetailRepository.getSumMaxByAllUserAndGame(gameEntity.get(), BetType.TAI);
            sumMinOfAll = gameDetailRepository.getSumMinByAllUserAndGame(gameEntity.get(), BetType.XIU);
        }
        DiceResult diceResult = new DiceResult(
                gameEntity.get().getDice1(),
                gameEntity.get().getDice2(),
                gameEntity.get().getDice3()
        );

        return new GameResponse(
                diceResult,
                sumMaxOfAll,
                sumMinOfAll,
                gameEntity.get().getStatus() ? GameStatus.STARTING.name()
                        : !(gameEntity.get().getCountdown() == 0)
                        ? GameStatus.BET_LOCKED.name()
                        : GameStatus.CLOSED.name(),
                second
        );
    }
}
