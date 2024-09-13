package OrderApplication.OrderControllerApi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestBody {

    private String itemname;
    private String picture;
    private String description;
    private double price;
    private int quantity;
}
