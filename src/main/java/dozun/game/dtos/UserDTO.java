package dozun.game.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String fullName;
    private UUID userId;
    private String username;
    private String email;
    private UUID id;
    private Boolean status;
    private String role;
    private List<String> roles;

    @JsonIgnore
    private String password;

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserDTO(String fullName, String password, String email, UUID id, Boolean status, String role, List<String> roles) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.id = id;
        this.status = status;
        this.role = role;
        this.roles = roles;
    }
}
