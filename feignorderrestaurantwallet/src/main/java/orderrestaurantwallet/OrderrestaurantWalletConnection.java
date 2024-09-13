package orderrestaurantwallet;


import jdk.jfr.RecordingState;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orderrestaurantwallet", url = "localhost:8085/api/restaurant/wallet")
public interface OrderrestaurantWalletConnection {


    @PostMapping("/transaction/save")
    public ResponseEntity<?> SaveTransactiononRstWallet(@RequestParam Long rsid,
                                                        @RequestBody TransactionRequestBody transactionRequestBody);
}
