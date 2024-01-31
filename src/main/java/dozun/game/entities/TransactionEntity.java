package dozun.game.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
@EntityListeners(AuditingEntityListener.class)
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;

    @Column
    private Double amount;

    @Column
    private Double balance;

    public TransactionEntity(WalletEntity wallet, Double amount, Double balance) {
        this.wallet = wallet;
        this.amount = amount;
        this.balance = balance;
    }
}
