package OrderApplication.OrderModel;

public enum Order_Status {

    CREATED, // by default for new orders
    ACCEPTED, // by restaurant after on new orders page
    REJECTED, // by restaurant  on new orders page
    READY, // by restaurant  on ready orders page

    ON_WAY_FOR_PICKUP, // assign courrier to the order
    PICKED_UP, // courrier arrived and picked up

    DELIVERED, // delievered
    NOT_DELIVERED // cant be delivered
}
