package CourrierApplication.JwtSecurityConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityFiltreChain {

    private final JwtAuthentication jwtAuthentication;


    public SecurityFiltreChain(JwtAuthentication jwtAuthentication) {
        this.jwtAuthentication = jwtAuthentication;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection
                .authorizeHttpRequests(authz -> authz

                        .requestMatchers("api/courrier/public/**").permitAll()
                        .requestMatchers("api/courrier/private/**").authenticated()

                ).addFilterBefore(jwtAuthentication, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
