package my.reservetable.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import my.reservetable.member.dto.LoginMemberDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    // todo : SECRET_KEY 등 숨기기
    private static final String SUBJECT = "subject@gmail.com";
    private static final String SECRET_KEY = "Q4NSl604sgyHJj1qwEkR3ycUeR4uUAt7WJraD7EN3O9DVM4yyYuHxMEbSF4XXyYJkal13eqgB0F7Bq4H";
    private static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; //일주일
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER = "Authorization";

    public static String createJwtToken(LoginMemberDetails member) {
        return Jwts.builder()
                //.subject(SUBJECT)
                .issuer("issuer@gmail.com")
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("id", member.getMemberDto().getId())
                .claim("email", member.getMemberDto().getEmail())
                .claim("roles", member.getMemberDto().getRole()+"")
                .claim("nickName", member.getMemberDto().getNickName())
                .compact();
    }
/*

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean verifyToken(String jwt) {

        Jwts.parser().verifyWith(getSigningKey()).build().parseEncryptedClaims(jwt);

        return true;
    }

*/

}
