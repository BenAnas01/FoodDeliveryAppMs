package restaurantwalletapplication.RestaurantWalletController;

import jakarta.transaction.Transactional;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurantwalletapplication.RestaurantWalletServices.RestaurantWalletRepo;
import restaurantwalletapplication.RestaurantWalletServices.RestaurantWalletServices;
import restaurantwalletapplication.RstaurantwalletModel.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/restaurant/wallet")
@AllArgsConstructor
public class RestaurantWalletControllerApi {


    private RestaurantWalletServices restaurantWalletServices;
    private TransactionServices transactionServices;
    private RestaurantWalletRepo restaurantWalletRepo;
    private WithdrawServices withdrawServices;

    // todo save new Wallet
    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestParam Long restaurantid){
        return restaurantWalletServices.RegisterNewWallet(restaurantid);

    }


    @GetMapping("/")
    public ResponseEntity<?> walletbyrestaurantid(@RequestParam Long walletid){

        return restaurantWalletServices.fetchwalletyRestaurantid(walletid);


    }


    // todo save new Transaction
    @Transactional
    @PostMapping("/transaction/save")
    public ResponseEntity<?> saveTransactions(@RequestParam Long rsid,
                                              @RequestBody TransactionRequestBody transactionRequestBody){

        Transaction transaction = new Transaction();
        Optional<RestaurantWallet> exisitingwallet = restaurantWalletServices.findWalletbyRestaurantid(rsid);

        RestaurantWallet rswallet = exisitingwallet.get();
        TransactionType transactionType = TransactionType.valueOf(transactionRequestBody.getTrnsactiontype());
        transaction.setAmount(transactionRequestBody.getAmount());
        transaction.setCustomerwalletid(transactionRequestBody.getCustomerwalletid());
        transaction.setOrderid(transactionRequestBody.getOrderid());
        transaction.setTransactionType(transactionType);
        transaction.setRestaurantwallet(rswallet);

        return restaurantWalletServices.SavenewTransaction(rsid, transaction);



    }


    @Transactional
    @PostMapping("/transaction/withdraw")
    public ResponseEntity<?> withdrawFunds(@RequestParam Long restid, @RequestBody TransactionRequestBody transactionRequestBody ){

       return null;

    }

    @GetMapping("/transaction/non-paid/{restaurantid}")
    public ResponseEntity<?> getNonPaidTransactionsByRestaurantId(@PathVariable Long rstid){

        return transactionServices.getNonPaidTransactions(rstid);
    }

    @GetMapping("/transactions/all")
    public ResponseEntity<?> alltransactionsbyResturantid(@RequestParam Long restid){

        return transactionServices.AlltransasactionsbyRestaurantid(restid);
    }

    @PostMapping("/transactions/withdraw/save")
    public ResponseEntity<?> Savewithdraw(@RequestParam Long restid, @RequestParam String bankaccount){

        return transactionServices.withdrawRequest(restid, bankaccount);
    }

    @GetMapping("/transactions/withdraw/all")
    public ResponseEntity<?> myallwithdraw(@RequestParam Long restid){

        return withdrawServices.fetchallwithdraw(restid);
    }


    // admin

    @GetMapping("/transactions/allwithdraws")

    public ResponseEntity<?> fetchallwithdraws(){


        return ResponseEntity.status(HttpStatus.ACCEPTED).body(withdrawServices.fetchallwitdraws());
    }


    @PutMapping("/transactions/update")
    public ResponseEntity<?> changewithdrawstatus(@RequestParam Long withdrawid){

       return withdrawServices.ConfirmWithdrawStatus(withdrawid);
    }
}
