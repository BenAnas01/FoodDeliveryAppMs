package restaurantwalletapplication.RestaurantWalletServices;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import restaurantwalletapplication.RstaurantwalletModel.RestaurantWallet;
import restaurantwalletapplication.RstaurantwalletModel.Transaction;
import restaurantwalletapplication.RstaurantwalletModel.TransactionsRepo;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RestaurantWalletServices {

    private RestaurantWalletRepo restaurantWalletRepo;

    private TransactionsRepo transactionsRepo;

    public ResponseEntity<?> RegisterNewWallet(Long restaurantid){

        RestaurantWallet restaurantWallet = new RestaurantWallet();
        restaurantWallet.setRestaurantid(restaurantid);
        restaurantWallet.setLastModification(LocalDateTime.now());


        try{

            restaurantWalletRepo.save(restaurantWallet);

            return  ResponseEntity.status(HttpStatus.ACCEPTED).build();

        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }




    public ResponseEntity<?> fetchwalletyRestaurantid(Long restid){

        try {
            Optional<RestaurantWallet> restaurantWallet  = restaurantWalletRepo.findByRestaurantid(restid);

            if (restaurantWallet.isPresent()){

                RestaurantWallet restaurantWallet1 = restaurantWallet.get();

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(restaurantWallet1);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    public Optional<RestaurantWallet> findWalletbyRestaurantid(Long rsid){

        try{

            return restaurantWalletRepo.findByRestaurantid(rsid);
        }
        catch (Exception e){
            throw  e;

        }
    }

    // save a transaction

    @Transactional
    public ResponseEntity<?> SavenewTransaction(Long rswalletid, Transaction transaction1){


        try{

            Optional<RestaurantWallet> existingRsWallet = restaurantWalletRepo.findByRestaurantid(rswalletid);

            if (existingRsWallet.isPresent()){

                RestaurantWallet rswallet = existingRsWallet.get();

                rswallet.setBalance(rswallet.getBalance() + transaction1.getAmount());

                Transaction transaction = new Transaction();

                transaction.setAmount(transaction1.getAmount());
                System.out.println(transaction1.getAmount());
                transaction.setPaid(transaction1.isPaid());
                transaction.setCustomerwalletid(transaction1.getCustomerwalletid());
                transaction.setOrderid(transaction1.getOrderid());
                transaction.setRestaurantwallet(rswallet);
                transaction.setTransactionType(transaction1.getTransactionType());
                transactionsRepo.save(transaction);
                restaurantWalletRepo.save(rswallet);

                return ResponseEntity.status(HttpStatus.ACCEPTED).build();


            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
        catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }



    }


    public ResponseEntity<?> UpdateWalletBalance(Long restwalletid,double newbalance){

        try{

            Optional<RestaurantWallet> existingRsWallet = restaurantWalletRepo.findByRestaurantid(restwalletid);

            if (existingRsWallet.isPresent()){

                RestaurantWallet restaurantWallet = existingRsWallet.get();
                restaurantWallet.setBalance(restaurantWallet.getBalance() - newbalance);
                restaurantWallet.setLastModification(LocalDateTime.now());
                restaurantWalletRepo.save(restaurantWallet);

                return ResponseEntity.status(HttpStatus.ACCEPTED).build();

            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();




        }

        catch (Exception e){

            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }


    }







}
