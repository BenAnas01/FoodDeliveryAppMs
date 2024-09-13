package CourrierWallet.CourrierModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;


@Repository
public interface CourrierwalletRepo extends JpaRepository<CourrierWallet, Long> {
}
