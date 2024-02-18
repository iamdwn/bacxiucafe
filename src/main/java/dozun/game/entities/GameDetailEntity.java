package dozun.game.entities;

import dozun.game.enums.BetType;
import dozun.game.enums.GameResult;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game_detail")
@EntityListeners(AuditingEntityListener.class)
public class  GameDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "gameDetail_id")
    private UUID id;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @Column
    private Double betAmount;

    @Column
    @Enumerated
    private BetType betType;

    @Column
    @Enumerated
    private GameResult gameResult;

    @Column
    private Boolean status;

    public GameDetailEntity(UserEntity user, GameEntity game, Double betAmount, BetType betType, GameResult gameResult, Boolean status) {
        this.user = user;
        this.game = game;
        this.betAmount = betAmount;
        this.betType = betType;
        this.gameResult = gameResult;
        this.status = status;
    }

    public GameDetailEntity(UserEntity user, GameEntity game, Double betAmount, BetType betType) {
        this.user = user;
        this.game = game;
        this.betAmount = betAmount;
        this.betType = betType;
    }
}
