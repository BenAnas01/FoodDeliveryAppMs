package restaurantwalletapplication.RestaurantWalletController;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurantwalletapplication.RstaurantwalletModel.RestaurantWallet;
import restaurantwalletapplication.RstaurantwalletModel.TransactionType;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestBody {

    private double amount;
    private Long customerwalletid;
    private Long Orderid;
    private String  trnsactiontype;
}
