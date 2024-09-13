package CourrierWallet.CourrierModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionid;
    private double amount;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courrier_wallet_id")
    private CourrierWallet courrier;
    private Long Orderid;
    private  boolean isPaid = false;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
