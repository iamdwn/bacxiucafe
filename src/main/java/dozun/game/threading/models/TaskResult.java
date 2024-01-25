package dozun.game.threading.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class TaskResult {
    private Long dice1;
    private Long dice2;
    private Long dice3;
}
