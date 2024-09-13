package CourrierWallet.CourrierModel;


import lombok.AllArgsConstructor;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourrierWalletService {


    private CourrierwalletRepo courrierwalletRepo;

    private TransactionRepo transactionRepo;

    private WIthdrawsServices wIthdrawsServices;

    private WIthdrawsRepo wIthdrawsRepo;

    public ResponseEntity<?> RegisterNewWallet(CourrierWallet courrierWallet){

        try{


            courrierwalletRepo.save(courrierWallet);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();

        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }
    }


    // create transaction

    public ResponseEntity<?> CreateTransaction(Long walletid, Long orderid){


        Optional<CourrierWallet> existingWalletid = courrierwalletRepo.findById(walletid);

        if (existingWalletid.isPresent()){

            try {
                CourrierWallet courrierWallet = existingWalletid.get();
                Transaction transaction = new Transaction();
                transaction.setCourrier(courrierWallet);
                transaction.setTransactionType(TransactionType.TOPUP_ORDER);
                transaction.setPaid(false);
                transaction.setAmount(10);
                transaction.setOrderid(orderid);

                // update the balance
                courrierWallet.setBalance(courrierWallet.getBalance() + transaction.getAmount());
                courrierWallet.setLastModification(LocalDateTime.now());
                // save changes

                // save transaction

                transactionRepo.save(transaction);
                // save courrier wallet
                courrierwalletRepo.save(courrierWallet);

                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }


        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    // fetch wallet and  all transactions

    public ResponseEntity<?> fetchwalletanTransactions(Long walletid){

        try {
            Optional<CourrierWallet> existingWallet = courrierwalletRepo.findById(walletid);

            if (existingWallet.isPresent()){

                CourrierWallet wallet = existingWallet.get();
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(wallet);

            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    public ResponseEntity<?> SaveWithdrawsTransactions(Long walletid, String Bankaccount){

        return wIthdrawsServices.SaveWithdraw(walletid, Bankaccount);
    }




    // feth all withdraw transactions


    public ResponseEntity<?> fetchallWithdraws(Long walletid){

        List<Withdraws> lisofWitdhraws = wIthdrawsRepo.listofWithdraws(walletid);

        if (!lisofWitdhraws.isEmpty())
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(lisofWitdhraws);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
