package restaurantwalletapplication.RstaurantwalletModel;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restaurantwalletapplication.RestaurantWalletServices.RestaurantWalletServices;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServices {

    TransactionsRepo transactionsRepo;
    WithdrawServices withdrawServices;

    public ResponseEntity<?> SaveTransaction(Transaction transaction){

        try{

            transactionsRepo.save(transaction);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Transaction Saved Successfully");

        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request cant be processed ...");
        }

    }



    public ResponseEntity<?> getNonPaidTransactions(Long restaurantid){


        try{

            List<Transaction> nonPaidTransactions = transactionsRepo.listofNonPaidTransactions(restaurantid);

            if (nonPaidTransactions.isEmpty()){

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You have No transactions to be paid");
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(nonPaidTransactions);


        }

        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CANT PROCESS THE REQUEST NOW ...");

        }
    }





    public ResponseEntity<?> AlltransasactionsbyRestaurantid(Long restid){

        try{

            List<Transaction> Transacts = transactionsRepo.ListofAllTransactions(restid);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Transacts);
        }

        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CANT PROCESS THE REQUEST NOW .");
        }
    }


    public ResponseEntity<?> withdrawRequest(Long resid, String Bankaccount){


        return withdrawServices.SaveWitdhraw(resid,Bankaccount);
    }


}
