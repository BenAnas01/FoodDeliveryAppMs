package OrderApplication.OrderServices;


import OrderApplication.OrderModel.Items;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Itemservices {

    private ItemRepo itemRepo;


    public void SaveOrderitems(Items items){

        try{

            itemRepo.save(items);

        }

        catch (Exception e){

            throw  e;
        }


    }
}
