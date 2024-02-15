package dozun.game.controllers;

import dozun.game.BacxiucafeApplication;
import dozun.game.repositories.GameRepository;
import dozun.game.services.ApplicationService;
import dozun.game.services.GameService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.CompletableFuture;

@RestController
//@CrossOrigin
@Tag(name = "Application")
//@Api(tags="Application")
@RequestMapping("api/v1/app")
@SecurityRequirement(name = "bearerAuth")
public class ApplicationController {
    private ApplicationService applicationService;
    private GameService gameService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ApplicationController(ApplicationService applicationService, GameService gameService) {
        this.applicationService = applicationService;
        this.gameService = gameService;
    }

//    @Transactional
//    @PostMapping("/restart")
//    public void deleteAndRestart() {
//        CompletableFuture<Void> future = gameService.pause();
//        future.thenRun(() -> entityManager.createQuery("DELETE FROM GameEntity ge WHERE ge.countdown > -1").executeUpdate());
//        gameService.start();
//    }
}
