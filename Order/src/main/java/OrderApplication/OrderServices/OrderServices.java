package OrderApplication.OrderServices;

import OrderApplication.OrderModel.Items;
import OrderApplication.OrderModel.Order_Status;
import OrderApplication.OrderModel.Orders;
import OrderApplication.OrderModel.Paymentmethod;
import feignCustomerWallet.CustomerWallet;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import orderrestaurantwallet.OrderrestaurantWalletConnection;
import orderrestaurantwallet.TransactionRequestBody;
import orderrestaurantwallet.TransactionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServices {

    private OrderRepo orderRepo;
    private Itemservices itemservices;
    private OrderrestaurantWalletConnection orderrestaurantWalletConnection;
    CustomerWallet customerWallet;



    @Transactional
    public ResponseEntity<?> SaveOrderWithCcOrCash(Orders order){

        try{

            Orders savedorder = orderRepo.save(order);

            TransactionRequestBody transactionRequestBody = new TransactionRequestBody();
            transactionRequestBody.setCustomerwalletid(order.getCustomerid());
            transactionRequestBody.setAmount(order.getAmount());
            transactionRequestBody.setTrnsactiontype(TransactionType.TOPUP_ORDER);
            transactionRequestBody.setOrderid(savedorder.getOrderid());
            ResponseEntity<?> response = orderrestaurantWalletConnection.SaveTransactiononRstWallet(order.getRestaurantid(), transactionRequestBody);

            if (!response.getStatusCode().is2xxSuccessful()){

                throw new RuntimeException("Transaction failed on restaurant wallet ...");
            }


            // make sure that restaurant wallet has been updated before saving order
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();


        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();



        }
    }

    @Transactional
    public ResponseEntity<?> SaveNewOrderWithWallet(Orders order){


        // make connection with customer wallet and save transaction

        try{

            // make connection with reaturant wallet and save transaction

            ResponseEntity res = customerWallet.PayOrder(order.getCustomerid(), order.getAmount());

            if(res.getStatusCode().is2xxSuccessful()){

                Orders savedorder = orderRepo.save(order);
                TransactionRequestBody transactionRequestBody = new TransactionRequestBody();
                transactionRequestBody.setCustomerwalletid(order.getCustomerid());
                transactionRequestBody.setAmount(order.getAmount());
                transactionRequestBody.setTrnsactiontype(TransactionType.TOPUP_ORDER);
                transactionRequestBody.setOrderid(savedorder.getOrderid());
                orderrestaurantWalletConnection.SaveTransactiononRstWallet(order.getRestaurantid(), transactionRequestBody);


                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();




        }
        catch (Exception e){

            System.out.println(e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //todo show all  new created orders for restaurant
    public ResponseEntity<?> allnewCreatedordersforRestaurantbyId(Long restid){

        try{

            List<Orders> listofAllOrders = orderRepo.allnewCreatedOrders(restid);

            if (!listofAllOrders.isEmpty()){


                return ResponseEntity.status(HttpStatus.ACCEPTED).body(listofAllOrders);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


        }

        catch (Exception e ){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }


    public ResponseEntity<?> ChangeorderStatus(Long orderid, String orderstatus){

       try{
           Optional<Orders> order = orderRepo.findById(orderid);

           if (order.isPresent()){

               Orders updatedorderStatus = order.get();
               updatedorderStatus.setOrderStatus(Order_Status.valueOf(orderstatus));

               orderRepo.save(updatedorderStatus);

               return ResponseEntity.status(HttpStatus.ACCEPTED).build();
           }

           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }

       catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }


    // todo all accepted orders by restaurant id
    public ResponseEntity<?> allAcceptedOrdersbyRestaurant(Long restid){

        try{

            List<Orders> listofAllOrders = orderRepo.allAccepetedOrders(restid);

            if (!listofAllOrders.isEmpty()){


                return ResponseEntity.status(HttpStatus.ACCEPTED).body(listofAllOrders);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


        }

        catch (Exception e ){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    // todo all rejected orders by restaurant id


    public ResponseEntity<?> AllrejectOrdersByRestId(Long restid){

        try{

            List<Orders> listofAllOrders = orderRepo.allRejectedorders(restid);

            if (!listofAllOrders.isEmpty()){


                return ResponseEntity.status(HttpStatus.ACCEPTED).body(listofAllOrders);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


        }

        catch (Exception e ){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }


    // todo all ready orders by restaurant id
    public ResponseEntity<?> AllReadyOrderbyRestaurant(Long restid){

        try{

            List<Orders> listofAllOrders = orderRepo.allorderreadyforRestaurant(restid);

            if (!listofAllOrders.isEmpty()){


                return ResponseEntity.status(HttpStatus.ACCEPTED).body(listofAllOrders);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


        }

        catch (Exception e ){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    // todo show restaurant all orders


    public ResponseEntity<?> showAllordersMadeforRestaurant(Long restaurantid){

        try{
            List<Orders> listofOrders = orderRepo.ordersbyRestaurantId(restaurantid);

            if (listofOrders.isEmpty()){

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(listofOrders);
        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }


    }



    // todo show customer all orders
    public ResponseEntity<?> showAllordersMadebyCustomer(Long customerid){

        try{
            List<Orders> listofOrders = orderRepo.ordersbyCustomerId(customerid);

            if (listofOrders.isEmpty()){

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(listofOrders);
        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }


    }


    public ResponseEntity<?> allfinishedordeers(Long restid){


        try{
            List<Orders> listofOrders = orderRepo.allfinishedordersforRestaurant(restid);

            if (listofOrders.isEmpty()){

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(listofOrders);
        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }


    }





    // todo show  all ready for and not assigned order  =>  orders.courrier == NULL;
    public ResponseEntity<?> showUnassginedOrders(){

        try{
            List<Orders> lisofUnassignedOrders = orderRepo.allordersReadyforPickup();

            if (lisofUnassignedOrders.isEmpty()){

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(lisofUnassignedOrders);
        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }

    }



    public ResponseEntity<?> updateorderforcourrier(Long courrierid, Long orderid, String status){

        try {
            Optional<Orders> existingorder = orderRepo.findById(orderid);

            if (existingorder.isPresent()){

                Orders updatedorder = existingorder.get();
                updatedorder.setCourierId(courrierid);
                updatedorder.setOrderStatus(Order_Status.valueOf(status));
                orderRepo.save(updatedorder);

                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }


    // todo show all accepted  or on way for pick up orders
    public ResponseEntity<?> onWayforPickup(Long courierid){

        try{
            Optional<Orders> ordertobepickedup = orderRepo.getorderbyidforCourier(courierid);

            if (!ordertobepickedup.isPresent()){

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Orders order = ordertobepickedup.get();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(order);
        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }

    }

    // put update order status and set it as pickedup

    // todo show all accepted  or on way for pick up orders
    public ResponseEntity<?> PickedupOrder(Long courierid){

        try{
            Optional<Orders> ordertobepickedup = orderRepo.PickedupOrder(courierid);

            if (!ordertobepickedup.isPresent()){

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Orders order = ordertobepickedup.get();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(order);
        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }

    }

    // update the order status set it as delivered or not




    // todo show last step of delivery  by courrier id
    public ResponseEntity<?> DeliveredORNotDeliveredOrder(Long courierid){

        try{
            Optional<Orders> ordertobepickedup = orderRepo.orderDeliveredornot(courierid);

            if (!ordertobepickedup.isPresent()){

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Orders order = ordertobepickedup.get();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(order);
        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }

    }


    // todo show all orders by courrier id

    public ResponseEntity<?> showAllorderforCourriers(Long courrierid){



        try{
            List<Orders> listofAllorders = orderRepo.Allordersbycourrierid(courrierid);

            if (listofAllorders.isEmpty()){

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(listofAllorders);
        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }


    }




    // todo show details of order for customer

    public ResponseEntity<?> orderdetailsforCustomer(Long orderid){

        try{
            Optional<Orders> order = orderRepo.getorderbyidforcustomer(orderid);

            if (order.isPresent()){

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(order);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("order id is not exist");
        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request cant be processed now");


        }


    }

    // todo show details of order for restaurant

    public ResponseEntity<?> orderdetailsforRestaurant(Long orderid){

        try{
            Optional<Orders> order = orderRepo.getorderbyidforResturant(orderid);

            if (order.isPresent()){

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(order);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("order id is not exist");
        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request cant be processed now");


        }


    }










}
