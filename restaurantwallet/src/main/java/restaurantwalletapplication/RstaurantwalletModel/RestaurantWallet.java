package restaurantwalletapplication.RstaurantwalletModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RestaurantWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletid;
    private double balance;
    private boolean iswalletactive = true;
    private String currency = "GBP";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime LastModification;
    private Long restaurantid;
    @OneToMany(mappedBy = "restaurantwallet", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transaction> transactions;



}
