package CourrierApplication.CourrierServices;

import CourrierApplication.CourrierModel.CourrierProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourrierProfileRepo extends JpaRepository<CourrierProfile, Long> {
}
