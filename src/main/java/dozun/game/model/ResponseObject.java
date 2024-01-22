package dozun.game.model;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject {
    private String status;
    private String message;
    private Object data;
}
