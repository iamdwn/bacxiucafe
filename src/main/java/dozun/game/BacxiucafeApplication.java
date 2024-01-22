package dozun.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"dozun.game.entities"})
public class BacxiucafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BacxiucafeApplication.class, args);
	}

}
