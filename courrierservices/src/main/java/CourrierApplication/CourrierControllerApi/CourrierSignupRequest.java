package CourrierApplication.CourrierControllerApi;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourrierSignupRequest {

    private String emailaddress;
    private String password;
}
