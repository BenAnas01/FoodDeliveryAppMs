package orderrestaurantwallet;



public class TransactionRequestBody {

    private double amount;
    private Long customerwalletid;
    private Long Orderid;
    private TransactionType  trnsactiontype;


    public TransactionRequestBody() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getCustomerwalletid() {
        return customerwalletid;
    }

    public void setCustomerwalletid(Long customerwalletid) {
        this.customerwalletid = customerwalletid;
    }

    public Long getOrderid() {
        return Orderid;
    }

    public void setOrderid(Long orderid) {
        Orderid = orderid;
    }

    public TransactionType getTrnsactiontype() {
        return trnsactiontype;
    }

    public void setTrnsactiontype(TransactionType trnsactiontype) {
        this.trnsactiontype = trnsactiontype;
    }
}
