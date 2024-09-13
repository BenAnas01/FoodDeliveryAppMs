package CourrierApplication.CourrierServices;

import CourrierApplication.CourrierModel.Courrier;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourrierRepo extends JpaRepository<Courrier, Long> {

    @Query("select cr from Courrier  cr where cr.emailaddress = :email")
    public Optional<Courrier> findByEmailaddress(@Param("email") String email);



}
