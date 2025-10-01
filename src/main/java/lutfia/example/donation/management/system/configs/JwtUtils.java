package lutfia.example.donation.management.system.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtils {
    private final String secretKey = "mySecretKey";
    private final long EXPIRATION = 1000 * 60 * 60;

public String generateToken(String email , Map<String ,Object>claims){
return Jwts.builder()
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
        .setSubject(email)
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .setClaims(claims)
        .compact();
}
public Claims getClaims(String token){
    return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .build()
            .parseClaimsJws(token)
            .getBody();
}

public String getEmailFromToken(String token){
    return getClaims(token).getSubject();
}

public boolean isTokenExpired(String token){
    Date expiration = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .build()
            .parseClaimsJws(token)
            .getBody().getExpiration();
    return expiration.before(new Date());
}

public boolean validateToken(String token){
    return getClaims(token).equals(getEmailFromToken(token))
            && !isTokenExpired(token);
}
public String generateAccessToken(String email){
    Map<String,Object>claims=new HashMap<>();
    return generateToken(email,claims);
}


}
