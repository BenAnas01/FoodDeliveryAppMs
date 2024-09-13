package CourrierApplication;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication

@EnableFeignClients(basePackages = "feignorderconnection")
public class CourrierApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourrierApplication.class, args);
    }
}
