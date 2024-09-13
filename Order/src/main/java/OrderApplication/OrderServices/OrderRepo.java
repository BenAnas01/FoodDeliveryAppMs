package OrderApplication.OrderServices;

import OrderApplication.OrderModel.Orders;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {

    // todo get order by customer id
    @Query("select o from Orders o where o.customerid = :id")
    List<Orders> ordersbyCustomerId(@Param("id") Long id);


    // todo get order details by order id
    @Query("select o from Orders o where o.orderid = :id")
    Optional<Orders> getorderbyidforcustomer(@Param("id") Long id);



    // todo  get all orders by restaurantd id
    @Query("select o from Orders o where o.restaurantid = :id")
    List<Orders> ordersbyRestaurantId(@Param("id") Long id);

    // todo get order by id
    @Query("select o from Orders o where o.orderid = :id")
    Optional<Orders> getorderbyidforResturant(@Param("id") Long id);

    // todo  all orders created by  rest id
    @Query("select  o from Orders  o where   o.restaurantid = :id AND o.orderStatus = 'CREATED'")
    List<Orders> allnewCreatedOrders(@Param("id") Long id);

    // todo  all orders accepted by  rest id
    @Query("select  o from Orders  o where   o.restaurantid = :id AND o.orderStatus = 'ACCEPTED'")
    List<Orders> allAccepetedOrders(@Param("id") Long id);


    // todo  all orders rejected by  rest id
    @Query("select  o from Orders  o where   o.restaurantid = :id AND o.orderStatus = 'REJECTED'")
    List<Orders> allRejectedorders(@Param("id") Long id);

    @Query("select  o from Orders  o where   o.restaurantid = :id AND o.orderStatus = 'READY' or o.orderStatus = 'ON_WAY_FOR_PICKUP'")
    List<Orders> allorderreadyforRestaurant(@Param("id") Long id);


    @Query("select  o from Orders  o where   o.restaurantid = :id AND o.orderStatus = 'PICKED_UP' or o.orderStatus = 'DELIVERED' or o.orderStatus = 'NOT_DELIVERED'")
    List<Orders> allfinishedordersforRestaurant(@Param("id") Long id);





    //  todo fetch all ready order for pick up  to display them to the courriers
    @Query("SELECT o FROM Orders o WHERE o.courierId IS NULL AND o.orderStatus = 'READY'")
     List<Orders> allordersReadyforPickup();



    // todo fetch all orders accepted by courrier
    @Query("select o from Orders o where o.courierId = :id AND o.orderStatus = 'ON_WAY_FOR_PICKUP'")
    Optional<Orders> getorderbyidforCourier(@Param("id") Long id);



    // todo on way for pick up

    @Query("select o from Orders o where o.courierId = :id AND o.orderStatus = 'PICKED_UP'")
    Optional<Orders> PickedupOrder(@Param("id") Long id);

    // todo mark order as delivered

    @Query("select o from Orders o where o.courierId = :id AND o.orderStatus = 'DELIVERED' or o.orderStatus = 'NOT_DELIVERED'")
    Optional<Orders> orderDeliveredornot(@Param("id") Long id);


    @Query("select o from Orders o where o.courierId = :id")
    List<Orders> Allordersbycourrierid(@Param("id") Long id);






}
