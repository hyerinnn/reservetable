package my.reservetable.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.auth.MemberInfo;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    // todo : SECRET_KEY 등 숨기기

    private static final String SUBJECT = "서브젝트";
    private static final String SECRET_KEY = "시크릿키";
    private static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; //일주일
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER = "Authorization";

    public static String createJwtToken(MemberInfo memberInfo) {
        return Jwts.builder()
                .subject(SUBJECT)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("id", memberInfo.getMemberDto().getId())
                .claim("email", memberInfo.getMemberDto().getEmail())
                .claim("role", memberInfo.getMemberDto().getRole()+"")
                .claim("nickName", memberInfo.getMemberDto().getNickName())
                .compact();
    }

/*    public static void verifyToken(String jwt) throws SignatureVerificationException, TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt);
        return decodedJWT;
    }*/



}
