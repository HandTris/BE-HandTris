package jungle.HandTris.global.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${spring.jwt.token.expiration}")
    private long validityInMilliseconds;
    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret.key}")String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String username, String nickname) {
        Date now = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .header()
                            .type("JWT")
                        .and()
                        .subject(username)
                        .claim("nickname", nickname)
                        .issuedAt(now)
                        .expiration(new Date(now.getTime() + validityInMilliseconds))
                        .signWith(secretKey)
                        .compact();
    }

    // 토큰 검증
    public String getUsername(String token) {

        return Jwts.parser().
                verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public String getNickname(String token) {

        return Jwts.parser().
                verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("nickname", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }
}
