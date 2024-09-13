package CourrierApplication.JwtSecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private final String  Secret_Key = "b665e720bdc7646eccc6d66e24875fdc218d7f47f330ec3d5c5588c98e9dcc6e";


    public String extractUserEmail(String token) {
        return extractClaim(token, claims -> claims.get("userEmail", String.class));
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = ExtractallClaims(token);
        return claimsResolver.apply(claims);
    }


    public Claims ExtractallClaims(String jwt){

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Secret_Key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userEmail", email);
        claims.put("userId", userId);
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
