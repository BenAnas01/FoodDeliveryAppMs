package CourrierWallet.CourrierModel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourrierWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletid;
    private double balance;
    private boolean iswalletactive = true;
    private String currency = "GBP";
    private LocalDateTime createdAt;
    private LocalDateTime LastModification;
    private Long courrierid;
    @OneToMany(mappedBy = "courrier", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transaction> transactions;
}
