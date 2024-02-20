package dozun.game.config;

import dozun.game.entities.GameEntity;
import dozun.game.enums.BetType;
import dozun.game.enums.GameStatus;
import dozun.game.repositories.GameRepository;
import dozun.game.services.GameService;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.DisposableBean;

import java.util.Optional;

@Component
public class ApplicationListenerConfig implements ApplicationListener<ApplicationReadyEvent> {

    private GameService gameService;
    private GameRepository gameRepository;

    @Autowired
    public ApplicationListenerConfig(GameService gameService, GameRepository gameRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        gameService.start();
    }
}
