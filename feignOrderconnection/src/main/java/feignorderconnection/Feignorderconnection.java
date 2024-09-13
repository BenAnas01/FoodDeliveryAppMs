package feignorderconnection;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "orders", url = "http://localhost:8086/api/order")
public interface Feignorderconnection {

    @GetMapping("/customer/order/{orderid}")
    public ResponseEntity<?>  orderbyid(@PathVariable Long orderid);


    @PostMapping("/save")
    public ResponseEntity<?> CustomerSaveOrder(@RequestBody OrderBody orderBody);


    // todo
    // fetch all orders by  customer id
    @GetMapping("/customer/all")
    public ResponseEntity<?> allorderwithCustomerid(@RequestParam Long customerid);


    // todo

    @GetMapping("/restaurant/order/all")
    public ResponseEntity<?> Allorder(@RequestParam Long restid);

    // fetch all order created for a restaurant id

    // todo show all  created orders with restaurantd id
    @GetMapping("/restaurant/order/created/all")
    public ResponseEntity<?> showAllCreatedorders(@RequestParam Long restid);

    // todo update order status for order
    // update order using restaurant  //  order status : accept , refuse ,  ready
    @PutMapping("/restaurant/order/update")
    public ResponseEntity<?> updateOrderstatus(@RequestParam Long orderid, @RequestParam String status);

    //fetch all orders accepted by  rest id

    // todo show all accepted orders by restaurant id
    @GetMapping("/restaurant/order/accept/all")
    public ResponseEntity<?> showAllAcceptedorders(@RequestParam Long restid);

    //fetch all orders ready by  rest id

    // todo show all ready orders by restaurant id
    @GetMapping("/restaurant/order/ready/all")
    public ResponseEntity<?> AllReadyOrders(@RequestParam Long restid);

    //fetch finished orders for a restaurant by  rest id
    @GetMapping("/restaurant/order/finished")
    public ResponseEntity<?> Allorderfinished(@RequestParam Long restid);


    // todo

    // fetch all orders , ready , to be displayed to all courriers

    @GetMapping("/courrier/no-courier")
    public ResponseEntity<?> courrierfetchAllUnAssignedOrders();


    // update order status  :

    @PutMapping("/courrier/update")
    public ResponseEntity<?> upadateOrdestatus(@RequestParam Long orderid, @RequestParam Long courrierid, @RequestParam String status);

    //fetch all orders accepted by  courrier id,
    @GetMapping("/courrier/accepted")
    public ResponseEntity<?> courrierfetchAllAcceptedorders(@RequestParam Long courrierid);

    //fetch all orders picked up by courrier ,

    @GetMapping("/courrier/pickedup")
    public ResponseEntity<?> courrierfetchAllpickeduporders(@RequestParam Long courrierid);


    //fetch  order delivered or not by courrier,

    @GetMapping("/courrier/finished")
    public ResponseEntity<?> courrierallfinishedOrders(@RequestParam Long courrierid);

    // fetch all orders by courrier id

    @GetMapping("/courrier/all")
    public ResponseEntity<?> courrierfetchallorders(@RequestParam Long courrierid);









}
