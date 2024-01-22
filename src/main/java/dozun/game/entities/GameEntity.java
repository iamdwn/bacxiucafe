package dozun.game.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game")
@EntityListeners(AuditingEntityListener.class)
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    private List<GameDetailEntity> games;

    @ManyToMany(mappedBy = "games")
    @JsonIgnore
    private List<UserEntity> users = new ArrayList<>();

    @Column
    private Long dice1;

    @Column
    private Long dice2;

    @Column
    private Long dice3;

    @Column
    private Boolean status;

    @Column
    private String result;

    public GameEntity(List<GameDetailEntity> games, List<UserEntity> users) {
        this.games = games;
        this.users = users;
    }

    public GameEntity(List<GameDetailEntity> games, List<UserEntity> users, Long dice1, Long dice2, Long dice3, Boolean status, String result) {
        this.games = games;
        this.users = users;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.dice3 = dice3;
        this.status = status;
        this.result = result;
    }
}
