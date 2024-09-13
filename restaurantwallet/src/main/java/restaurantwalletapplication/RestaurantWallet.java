package restaurantwalletapplication;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class RestaurantWallet {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantWallet.class, args);
    }
}


