package dozun.game.models;

import dozun.game.enums.ResponseStatus;
import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject {
    private ResponseStatus status;
    private String message;
    private Object data;
}
