package CourrierWallet.CourrierModel;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WIthdrawsServices {

    private WIthdrawsRepo wIthdrawsRepo;

    private CourrierwalletRepo courrierwalletRepo;

    private TransactionRepo transactionRepo;


    @Transactional
    public ResponseEntity<?> SaveWithdraw(Long walletid, String Bankaccount){

        // check the balance


        try {
            Optional<CourrierWallet> existingwallet = courrierwalletRepo.findById(walletid);

            List<Transaction> allnonpaidTransactions = transactionRepo.allnonPaidTransactions(walletid);

            // check validity of wallet and existing of non paid orders

            if (existingwallet.isPresent()){



                CourrierWallet wallet = existingwallet.get();

                System.out.println(wallet.getWalletid());
                double sumofnonPaidorders = allnonpaidTransactions.stream().mapToDouble(Transaction::getAmount).sum();

                if (wallet.getBalance() > 0){


                    Withdraws withdraw =  new Withdraws();

                    withdraw.setStatus(WithdrawStatus.PENDING);
                    withdraw.setCreatedat(LocalDateTime.now());
                    withdraw.setBankaccount(Bankaccount);
                    withdraw.setCourrierwalletid(wallet.getWalletid());
                    withdraw.setAmount(sumofnonPaidorders);
                    wIthdrawsRepo.save(withdraw);

                    allnonpaidTransactions.forEach(transaction -> transaction.setPaid(true));
                    transactionRepo.saveAll(allnonpaidTransactions);
                    wallet.setBalance(wallet.getBalance() - sumofnonPaidorders);
                    wallet.setLastModification(LocalDateTime.now());
                    courrierwalletRepo.save(wallet);

                    return ResponseEntity.status(HttpStatus.ACCEPTED).build();

                }

                return ResponseEntity.status(HttpStatusCode.valueOf(402)).body("UNSUFFCIENT FUNDS  ...");

            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }



}
