package restaurantwalletapplication.RstaurantwalletModel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionid;
    private double amount;
    private Long customerwalletid;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_wallet_id")
    private RestaurantWallet restaurantwallet;

    private Long Orderid;
    private  boolean isPaid = false;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

}
