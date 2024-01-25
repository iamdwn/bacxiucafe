package dozun.game.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dozun.game.constants.BetType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private BetType type;

    @Column
    private LocalDateTime gameStart;

    @Column
    private Long duration;

    public GameEntity(List<GameDetailEntity> games, List<UserEntity> users) {
        this.games = games;
        this.users = users;
    }

    public GameEntity(List<GameDetailEntity> games, List<UserEntity> users, Long dice1, Long dice2, Long dice3, BetType type) {
        this.games = games;
        this.users = users;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.dice3 = dice3;
        this.type = type;
    }

    public GameEntity(Long dice1, Long dice2, Long dice3, BetType type, LocalDateTime gameStart, Long duration) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.dice3 = dice3;
        this.type = type;
        this.gameStart = gameStart;
        this.duration = duration;
    }
}
