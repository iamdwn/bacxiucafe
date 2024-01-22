package dozun.game.dtos;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RandomStuff {

    private final String message;

    public RandomStuff(String message) {
        this.message = message;
    }

}
