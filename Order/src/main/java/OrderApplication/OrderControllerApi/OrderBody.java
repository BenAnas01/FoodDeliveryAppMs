package OrderApplication.OrderControllerApi;


import OrderApplication.OrderModel.Items;
import OrderApplication.OrderModel.Order_Status;
import OrderApplication.OrderModel.Paymentmethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderBody {

    private List<ItemRequestBody> items;
    private Long restaurantid;
    private Long customerid;
    private String daddress;
    private String paddress;
    private Paymentmethod Paymentmethod;
    private double amount;

}
