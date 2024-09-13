package OrderApplication.OrderModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderid;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Items> items;
    private Long restaurantid;
    private Long customerid;
    private String daddress;
    private String paddress;
    @Enumerated(EnumType.STRING)
    private Paymentmethod Paymentmethod;
    private double amount;
    @Enumerated(EnumType.STRING)
    private Order_Status orderStatus = Order_Status.CREATED;
    private boolean ispaid;
    private double amounttocollect;
    private Long courierId;

}
