package dozun.game.services;

import dozun.game.BacxiucafeApplication;
import dozun.game.repositories.GameRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class ApplicationService {
    private GameRepository gameRepository;
    private ConfigurableApplicationContext context;
    @PersistenceContext
    private EntityManager entityManager;
    public ApplicationService(GameRepository gameRepository, ConfigurableApplicationContext context) {
        this.gameRepository = gameRepository;
        this.context = context;
    }

//    @Async
//    public CompletableFuture<Void> deleteDataAsync() {
//        gameRepository.deleteByCountdownGreaterThan(-1  );
//        return CompletableFuture.completedFuture(null);
//    }
//
//    public void deleteData() {
//        gameRepository.deleteByCountdownGreaterThan(-1  );
//    }


//    public void restart() {
//        SpringApplication.exit(context, () -> 0);
//        SpringApplication.run(BacxiucafeApplication.class);
//    }
}
