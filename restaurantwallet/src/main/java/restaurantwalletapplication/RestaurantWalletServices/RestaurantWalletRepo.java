package restaurantwalletapplication.RestaurantWalletServices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restaurantwalletapplication.RstaurantwalletModel.RestaurantWallet;

import java.util.Optional;

@Repository
public interface RestaurantWalletRepo extends JpaRepository<RestaurantWallet, Long> {


    Optional<RestaurantWallet> findByRestaurantid(Long restaurantid);

}
