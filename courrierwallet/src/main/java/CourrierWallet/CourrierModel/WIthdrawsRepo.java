package CourrierWallet.CourrierModel;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WIthdrawsRepo extends JpaRepository<Withdraws, Long> {


    @Query("select  wt from Withdraws  wt where wt.courrierwalletid = :id")
    List<Withdraws> listofWithdraws(@Param("id") Long id);
}
