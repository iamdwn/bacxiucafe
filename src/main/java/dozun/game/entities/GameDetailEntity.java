package dozun.game.entities;

import dozun.game.constants.BetType;
import dozun.game.constants.GameResult;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game_detail")
@EntityListeners(AuditingEntityListener.class)
public class GameDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @Column
    private Float betAmount;

    @Column
    @Enumerated
    private BetType betType;

    @Column
    @Enumerated
    private GameResult gameResult;

    public GameDetailEntity(UserEntity user, GameEntity game, Float betAmount, BetType betType, GameResult gameResult) {
        this.user = user;
        this.game = game;
        this.betAmount = betAmount;
        this.betType = betType;
        this.gameResult = gameResult;
    }
}
