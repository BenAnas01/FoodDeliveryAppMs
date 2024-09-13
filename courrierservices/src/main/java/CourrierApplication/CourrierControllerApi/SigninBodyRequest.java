package CourrierApplication.CourrierControllerApi;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SigninBodyRequest {

    private String emailaddress;
    private String psw;
}
