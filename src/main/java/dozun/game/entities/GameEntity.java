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
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game")
@EntityListeners(AuditingEntityListener.class)
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    private List<GameDetailEntity> games;

    @ManyToMany(mappedBy = "games")
    @JsonIgnore
    private List<UserEntity> users = new ArrayList<>();

    @Column
    private int dice1;

    @Column
    private int dice2;

    @Column
    private int dice3;

    @Column
    private BetType type;

    @Column
    private Date gameStart;

    @Column
    private int duration;

    @Column
    private Boolean status;

    public GameEntity(List<GameDetailEntity> games, List<UserEntity> users) {
        this.games = games;
        this.users = users;
    }

    public GameEntity(List<GameDetailEntity> games, List<UserEntity> users, int dice1, int dice2, int dice3, BetType type) {
        this.games = games;
        this.users = users;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.dice3 = dice3;
        this.type = type;
    }

    public GameEntity(int dice1, int dice2, int dice3, BetType type, Date gameStart, int duration, Boolean status) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.dice3 = dice3;
        this.type = type;
        this.gameStart = gameStart;
        this.duration = duration;
        this.status = status;
    }
}
