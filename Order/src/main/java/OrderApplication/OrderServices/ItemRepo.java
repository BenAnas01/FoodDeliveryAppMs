package OrderApplication.OrderServices;

import OrderApplication.OrderModel.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepo extends JpaRepository<Items, Long> {
}
