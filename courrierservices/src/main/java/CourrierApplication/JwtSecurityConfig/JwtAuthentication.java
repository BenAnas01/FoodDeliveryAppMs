package CourrierApplication.JwtSecurityConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Configuration
public class JwtAuthentication extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthentication(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull  HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String useremail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            log.info("NO JWT FOUND");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        useremail = jwtService.extractUserEmail(jwt);

        if (useremail != null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(useremail);


            UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(userDetails, null , null);

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }



        filterChain.doFilter(request, response);
    }
}
