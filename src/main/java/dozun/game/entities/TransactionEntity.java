package dozun.game.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
@EntityListeners(AuditingEntityListener.class)
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;

    @Column
    private Float amount;

    @Column
    private Float balance;

    public TransactionEntity(WalletEntity wallet, Float amount, Float balance) {
        this.wallet = wallet;
        this.amount = amount;
        this.balance = balance;
    }
}
