package feignRestrauwallet;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "customerwallet", url = "http://localhost:8085/")
public interface RestauWallet {

    @PostMapping("/api/restaurant/wallet/register")
    public ResponseEntity<String> SavenewWallet(@RequestParam Long restaurantid);
    @GetMapping("api/restaurant/wallet/transactions/all")
    public ResponseEntity<?> alltransactionsbyRestaurantid(@RequestParam Long restid);

    @PostMapping("api/restaurant/wallet/transactions/withdraw/save")
    public ResponseEntity<?> SaveWithdraw(@RequestParam Long restid, @RequestParam String bankaccount);

    @GetMapping("api/restaurant/wallet/transactions/withdraw/all")
    public ResponseEntity<?> MyWitdhraws(@RequestParam Long restid);

    @GetMapping("api/restaurant/wallet/")
    public ResponseEntity<?> walletbyrestaurantid(@RequestParam Long walletid);

}
