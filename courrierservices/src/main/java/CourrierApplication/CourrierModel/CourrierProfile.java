package CourrierApplication.CourrierModel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourrierProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courrierProfileid;
    private String fullName;
    private String profileImage;
    private String phonenumber;
    private LocalDateTime LastModification;
    private String BankAccountNumber;
    private boolean isModeActive;
    @OneToOne
    @JoinColumn(name = "courrier_id")
    @JsonIgnore
    private Courrier courrier;


}
