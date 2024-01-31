package dozun.game.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet")
@EntityListeners(AuditingEntityListener.class)
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private UserEntity user;

    @OneToMany(mappedBy = "wallet")
    private List<TransactionEntity> transactions;

    @Column
    private Double balance;

    public WalletEntity(UserEntity user, Double balance) {
        this.user = user;
        this.balance = balance;
    }
}
