package lutfia.example.donation.management.system.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lutfia.example.donation.management.system.model.Role;
import lutfia.example.donation.management.system.model.Users;
import lutfia.example.donation.management.system.repository.UserRepo;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class JwtUtils {
    private final String secretKey = "JA3UM02kKWwR1y4jWynkdOlINjF27WfH";
    private final long EXPIRATION = 1000 * 60 * 60;
    private final UserRepo userRepo;

public String generateToken(Users  user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("email",user.getEmail());
    claims.put("name",user.getName());
    // Store only role names (to avoid HashMap/List casting issues)
    List<String> roles = user.getRoles()
            .stream()
            .map(role -> role.getName())
            .collect(Collectors.toList());

    claims.put("roles", roles);
return Jwts.builder()
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
        .setSubject(user.getEmail())
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
    Users user = userRepo.findByEmail(email).orElseThrow();
    Map<String,Object>claims=new HashMap<>();
//    claims.put("roles",user.getRoles());
    claims.put("roles", user.getRoles()
            .stream()
            .map(Role::getName)
            .collect(Collectors.toList()));
    return generateToken(user);
}
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey) // Use your actual secret key
                .parseClaimsJws(token)
                .getBody();

        Object rolesObject = claims.get("roles"); // or "authorities" depending on your token

        if (rolesObject instanceof List<?>) {
            return ((List<?>) rolesObject).stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }


}


