package restaurantwalletapplication.RstaurantwalletModel;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WithdrawRepo extends JpaRepository<Withdraws, Long> {


    @Query("select wid from Withdraws  wid where wid.restaurantid = :id")
    public List<Withdraws> listofWithdraws(@Param("id") Long id);


}
