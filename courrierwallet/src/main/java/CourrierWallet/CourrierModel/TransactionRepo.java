package CourrierWallet.CourrierModel;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepo  extends JpaRepository<Transaction, Long> {

    @Query("select tr from Transaction  tr where tr.courrier.walletid = :id AND tr.isPaid = FALSE")
    List<Transaction> allnonPaidTransactions(@Param("id") Long id);
}
