package feignorderconnection;


import java.util.List;

public class OrderBody {

    private List<ItemRequestBody> items;
    private Long restaurantid;
    private Long customerid;
    private String daddress;
    private String paddress;
    private Paymentmethod Paymentmethod;
    private double amount;

    public OrderBody() {
    }

    public List<ItemRequestBody> getItems() {
        return items;
    }

    public void setItems(List<ItemRequestBody> items) {
        this.items = items;
    }

    public Long getRestaurantid() {
        return restaurantid;
    }

    public void setRestaurantid(Long restaurantid) {
        this.restaurantid = restaurantid;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public String getDaddress() {
        return daddress;
    }

    public void setDaddress(String daddress) {
        this.daddress = daddress;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public feignorderconnection.Paymentmethod getPaymentmethod() {
        return Paymentmethod;
    }

    public void setPaymentmethod(feignorderconnection.Paymentmethod paymentmethod) {
        Paymentmethod = paymentmethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
