package restaurantwalletapplication.RstaurantwalletModel;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import restaurantwalletapplication.RestaurantWalletServices.RestaurantWalletServices;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WithdrawServices {


    private WithdrawRepo withdrawRepo;

    private TransactionsRepo transactionsRepo;

    RestaurantWalletServices restaurantWalletServices;


    private  double sumofNonPaidOrders(Long restid){


        List<Transaction> nonpaidTransactions = transactionsRepo.listofNonPaidTransactions(restid);

        double sumofnonPaid = nonpaidTransactions.stream().mapToDouble(Transaction::getAmount).sum();

        return sumofnonPaid;

    }

    public boolean MarkTransactionAsPaid(Long restid){

        try{

            List<Transaction> nonPaidTransactions = transactionsRepo.listofNonPaidTransactions(restid);
            nonPaidTransactions.forEach(transaction -> transaction.setPaid(true));
            transactionsRepo.saveAll(nonPaidTransactions);

            return true;


        }

        catch (Exception e)
        {


            return false;
        }
    }



    @Transactional
    public ResponseEntity<?> SaveWitdhraw(Long restid, String Bankaccount){


        try {

            // {  to be sent from restaurant service to wallet through OpenFeign

            // get all non paid transactions first + get the amount
            // get the bank account from restaurant profile;


            // }

            Withdraws withdraw = new Withdraws();
            withdraw.setAmount(sumofNonPaidOrders(restid));
            withdraw.setBankaccount(Bankaccount);
            withdraw.setCreatedat(LocalDateTime.now());
            withdraw.setRestaurantid(restid);
            withdraw.setStatus(WithdrawStatus.PENDING);

            boolean isPaidtransaction = MarkTransactionAsPaid(restid);

            if (isPaidtransaction) {
                withdrawRepo.save(withdraw);
                // set the wallet balance - amount
                restaurantWalletServices.UpdateWalletBalance(restid, withdraw.getAmount());
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }

            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

        }

        catch (Exception e){

            System.out.println();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public ResponseEntity<?> fetchallwithdraw(Long restid){

        List<Withdraws> listofWithdraws = withdrawRepo.listofWithdraws(restid);

        if (!listofWithdraws.isEmpty()){

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(listofWithdraws);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    public ResponseEntity<?> fetchallwitdraws(){

        List<Withdraws>  listofWithdraws = withdrawRepo.findAll();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(listofWithdraws);
    }



    public ResponseEntity<?> ConfirmWithdrawStatus(Long withdrawid){

        Optional<Withdraws> exstingwithdraw = withdrawRepo.findById(withdrawid);


        if (exstingwithdraw.isPresent()){

            Withdraws updatedwithdraw = exstingwithdraw.get();

            updatedwithdraw.setStatus(WithdrawStatus.COMPLETED);

            withdrawRepo.save(updatedwithdraw);

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
