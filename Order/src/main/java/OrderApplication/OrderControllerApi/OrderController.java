package OrderApplication.OrderControllerApi;


import OrderApplication.OrderModel.Items;
import OrderApplication.OrderModel.Orders;
import OrderApplication.OrderModel.Paymentmethod;
import OrderApplication.OrderServices.ItemRepo;
import OrderApplication.OrderServices.OrderServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/order")
@AllArgsConstructor
public class OrderController {


    private OrderServices orderServices;

    private ItemRepo itemRepo;

    @PostMapping("/save")
    public ResponseEntity<?> SaveOrder(@RequestBody OrderBody orderBody){

        System.out.println(orderBody.getDaddress());

        System.out.println(orderBody.getPaddress());

        Orders orders  = new Orders();
        orders.setRestaurantid(orderBody.getRestaurantid());
        orders.setCustomerid(orderBody.getCustomerid());
        orders.setDaddress(orderBody.getDaddress());
        orders.setPaddress(orderBody.getPaddress());
        orders.setPaymentmethod(orderBody.getPaymentmethod());
        orders.setAmount(orderBody.getAmount());

        List<Items> itemsList = orderBody.getItems().stream().map(this::convertToEntity).collect(Collectors.toList());
        itemsList.forEach(item -> item.setOrder(orders));
        orders.setItems(itemsList);


        if (orderBody.getPaymentmethod() == Paymentmethod.WALLET ){

            orders.setAmounttocollect(0.00);
            orders.setIspaid(true);

           return orderServices.SaveNewOrderWithWallet(orders);

        }
        else if (orderBody.getPaymentmethod() == Paymentmethod.CC){

            orders.setAmounttocollect(0.00);
            orders.setIspaid(true);
            return orderServices.SaveOrderWithCcOrCash(orders);

        }

        orders.setAmounttocollect(orderBody.getAmount());
        orders.setIspaid(false);

        return orderServices.SaveOrderWithCcOrCash(orders);

    }

    // convert itembody to item

    private Items convertToEntity(ItemRequestBody itemRequestBody) {
        Items item = new Items();
        item.setItemName(itemRequestBody.getItemname());
        item.setItemPicture(itemRequestBody.getPicture());
        item.setDescription(itemRequestBody.getDescription());
        item.setPrice(itemRequestBody.getPrice());
        item.setQuantity(itemRequestBody.getQuantity());
        return item;

}


    // todo show all  created orders with restaurantd id
    @GetMapping("/restaurant/order/created/all")
    public ResponseEntity<?> showAllCreatedorders(@RequestParam Long restid){

        return orderServices.allnewCreatedordersforRestaurantbyId(restid);
    }

    // todo make updates on order


    @PutMapping("/restaurant/order/update")
    public ResponseEntity<?> updateOrderstatus(@RequestParam Long orderid, @RequestParam String status){

        return orderServices.ChangeorderStatus(orderid,status);
    }



    // todo show all accepted orders by restaurant id
    @GetMapping("/restaurant/order/accept/all")
    public ResponseEntity<?> showAllAcceptedorders(@RequestParam Long restid){

        return orderServices.allAcceptedOrdersbyRestaurant(restid);
    }



    // todo show all ready orders by restaurant id
    @GetMapping("/restaurant/order/ready/all")
    public ResponseEntity<?> AllReadyOrders(@RequestParam Long restid){

        return orderServices.AllReadyOrderbyRestaurant(restid);
    }


    // todo  show all finished made for restaurants
    @GetMapping("/restaurant/order/finished")
    public ResponseEntity<?> Allorderfinished(@RequestParam Long restid){

        return orderServices.allfinishedordeers(restid);
    }


    // todo  show all  orders for restaurants
    @GetMapping("/restaurant/order/all")
    public ResponseEntity<?> Allorder(@RequestParam Long restid){

        return orderServices.showAllordersMadeforRestaurant(restid);
    }










    // todo courrier part





    // todo show all Unsigned orders for courriers
    @GetMapping("/courrier/no-courier")
    public ResponseEntity<?> courrierfetchAllUnAssignedOrders(){

        return orderServices.showUnassginedOrders();
    }


    // Accept for pick an order

    @PutMapping("/courrier/update")
    public ResponseEntity<?> upadateOrdestatus(@RequestParam Long orderid,
                                               @RequestParam Long courrierid,
                                               @RequestParam String status){

       return orderServices.updateorderforcourrier(courrierid,orderid,status);


    }


    // todo show all accepted orders for courriers
    @GetMapping("/courrier/accepted")
    public ResponseEntity<?> courrierfetchAllAcceptedorders(@RequestParam Long courrierid){

        return orderServices.onWayforPickup(courrierid);
    }



    // todo show all pickedup orders for courriers
    @GetMapping("/courrier/pickedup")
    public ResponseEntity<?> courrierfetchAllpickeduporders(@RequestParam Long courrierid){

        return orderServices.PickedupOrder(courrierid);
    }




    // todo show all finished orders for courrier


    @GetMapping("/courrier/finished")
    public ResponseEntity<?> courrierallfinishedOrders(@RequestParam Long courrierid){

        return orderServices.DeliveredORNotDeliveredOrder(courrierid);
    }


    @GetMapping("/courrier/all")
    public ResponseEntity<?> courrierfetchallorders(@RequestParam Long courrierid){

        return orderServices.showAllorderforCourriers(courrierid);
    }

    // todo make updates on order

    // set the update for order  {ONWAYFORPICK UP , PICKED UP  ,  ONWAYFOR DELIVERY , DELIVERED, CANT BE DELIVERED}







    // todo customer part





    // todo get all order with customer id , the customer id to be extracted from JWT
    @GetMapping("/customer/all")
    public ResponseEntity<?> allorderwithCustomerid(@RequestParam Long customerid){

        return orderServices.showAllordersMadebyCustomer(customerid);
    }



    // todo  get order details  with order id for customer
    @GetMapping("/customer/order/{orderid}")
    public ResponseEntity<?> orderDetailsCustomer(@PathVariable Long orderid){

        return orderServices.orderdetailsforCustomer(orderid);
    }









}
