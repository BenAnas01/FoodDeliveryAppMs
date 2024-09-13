package OrderApplication.OrderModel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Itemid;
    private String ItemName;
    private String itemPicture;
    private String Description;
    private double price;
    private int quantity;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Orders order;

}
