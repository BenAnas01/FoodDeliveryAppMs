package CourrierApplication.CourrierControllerApi;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetProfileRequesBody {

    private String fullName;
    private MultipartFile profileImage;
    private String phonenumber;
    private String BankAccountNumber;

}
