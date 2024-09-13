package CourrierApplication.JwtSecurityConfig;


import CourrierApplication.CourrierServices.CourrierRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {




    private CourrierRepo courrierRepo;

    @Bean
    public UserDetailsService userDetailsService() {

        return username ->  courrierRepo.findByEmailaddress(username).orElseThrow(() -> new RuntimeException("not found"));
    }
}
