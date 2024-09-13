package restaurantwalletapplication.RstaurantwalletModel;

import feign.Param;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepo extends JpaRepository<Transaction, Long> {

    // find transactions non paid for restaurant by id



    @Query("select tr from Transaction tr where tr.restaurantwallet.restaurantid  = :id AND tr.isPaid = false")
    List<Transaction> listofNonPaidTransactions(@Param("id") Long id);

    @Query("select tr from Transaction  tr where tr.restaurantwallet.restaurantid = :id")
    List<Transaction> ListofAllTransactions(@Param("id") Long id);

}
