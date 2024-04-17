package my.reservetable.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.member.dto.MemberDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenProvider {
    // todo : SECRET_KEY 등 숨기기
    private static final String SUBJECT = "subject@gmail.com";
    private static final String SECRET_KEY = "Q4NSl604sgyHJj1qwEkR3ycUeR4uUAt7WJraD7EN3O9DVM4yyYuHxMEbSF4XXyYJkal13eqgB0F7Bq4H";
    private static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; //일주일
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER = "Authorization";

    private static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public static String createJwtToken(MemberDto member, String role) {
        return Jwts.builder()
                //.subject(SUBJECT)
                .issuer("issuer@gmail.com")
                .signWith(getSigningKey())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("id", member.getId())
                .claim("email", member.getEmail())
                .claim("role", member.getRole())
                .claim("nickName", member.getNickName())
                .compact();
    }

    public List<String> getRoles(String jwt){
        Claims  claims = Jwts.parser().verifyWith(getSigningKey()).build().parseClaimsJws(jwt).getPayload();
        return (List<String>) claims.get("roles");
    }

/*
    public String getRoles(String jwt){
        Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(jwt).getPayload();
                //.parseClaimsJws(jwt).getPayload();
        log.info("=============================== = {}",claims.get("roles"));
        return claims.get("roles").toString();
    }
*/

    public String getEmail(String jwt){
        Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseClaimsJws(jwt).getPayload();
        return claims.get("email").toString();
    }

    public Authentication getAuthentication(String jwt){
        Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseClaimsJws(jwt).getPayload();

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add((new SimpleGrantedAuthority((String) claims.get("role"))));

/*
        List<GrantedAuthority> roles = getRoles(jwt).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());*/
        return new UsernamePasswordAuthenticationToken(getEmail(jwt),null,authorities);
    }

    public boolean verifyToken(String jwt) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(jwt);
            return true;
        }
        catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.: {}", e.getMessage());
        }
        return false;
    }
}
