package store.polyfood.thuctap.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import store.polyfood.thuctap.models.entities.Account;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private Long jwtExpirationMs;

    public String generateToken(Authentication authentication) throws UnsupportedEncodingException {
        Account account = (Account) authentication.getPrincipal();
        return createToken(account.getAuthorities(), account.getUsername());
    }

    private String createToken(Collection<? extends GrantedAuthority> authorities, String subject) throws UnsupportedEncodingException {

        Calendar calendar = Calendar.getInstance();
        TimeZone gmt7TimeZone = TimeZone.getTimeZone("GMT+7");
        calendar.setTimeZone(gmt7TimeZone);
        Date now = calendar.getTime();
        Date expirationDate = new Date(now.getTime() + jwtExpirationMs);
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());

        return JWT.create()
                .withSubject(subject)
                .withClaim("role", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(generateSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey generateSecretKey() {
        try {
            KeySpec keySpec = new PBEKeySpec(
                    jwtSecret.toCharArray(),
                    jwtSecret.getBytes(),
                    10000,
                    256
            );
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return new javax.crypto.spec.SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error generating secret key", e);
        }
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }
}
