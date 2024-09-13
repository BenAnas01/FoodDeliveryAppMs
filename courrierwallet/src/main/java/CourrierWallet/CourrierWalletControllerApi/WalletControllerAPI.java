package CourrierWallet.CourrierWalletControllerApi;


import CourrierWallet.CourrierModel.CourrierWallet;
import CourrierWallet.CourrierModel.CourrierWalletService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/courrier/wallet")

@AllArgsConstructor
public class WalletControllerAPI {


    private CourrierWalletService courrierWalletService;

    @PostMapping("/save")
    public ResponseEntity<?> RegisterWallet(@RequestParam Long courrierid){


        CourrierWallet courrierWallet = new CourrierWallet();

        courrierWallet.setBalance(0);
        courrierWallet.setLastModification(LocalDateTime.now());
        courrierWallet.setCourrierid(courrierid);
        courrierWallet.setCreatedAt(LocalDateTime.now());

        return courrierWalletService.RegisterNewWallet(courrierWallet);
    }


    @PostMapping("/transaction/save")
    public ResponseEntity<?>  saveTransaction(@RequestParam Long walletid , @RequestParam Long orderid){

        return courrierWalletService.CreateTransaction(walletid, orderid);
    }


    @GetMapping("/{walletid}")
    public ResponseEntity<?> fetchwalletAndtransactions(@PathVariable Long walletid){

        return courrierWalletService.fetchwalletanTransactions(walletid);
    }

    @PostMapping("/withdraw/save")
    public ResponseEntity<?> saveWithdrawTransaction(@RequestParam Long walletid, @RequestParam String bankaccount){

        return courrierWalletService.SaveWithdrawsTransactions(walletid,bankaccount);
    }

    @GetMapping("/withdraw/all")
    public ResponseEntity<?> allwitdhraws(@RequestParam Long walletid){
        return courrierWalletService.fetchallWithdraws(walletid);
    }
}
