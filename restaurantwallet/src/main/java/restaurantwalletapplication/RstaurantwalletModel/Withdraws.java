package restaurantwalletapplication.RstaurantwalletModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Withdraws {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionid;
    private double amount;
    @Enumerated(EnumType.STRING)
    private WithdrawStatus status;
    private String Bankaccount;
    private Long restaurantid;
    private LocalDateTime Createdat;
}
