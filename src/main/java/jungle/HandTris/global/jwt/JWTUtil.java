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
    public static final String REFRESH_HEADER = "Refresh-Token";
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${spring.jwt.access.expiration}")
    private long validityInMillisecondsAccess;

    @Value("${spring.jwt.refresh.expiration}")
    private long validityInMillisecondsRefresh;

    @Value("${spring.jwt.issuer}")
    private String issuer;

    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
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

    // Access 토큰 생성
    public String createAccessToken(String nickname) {
        Date now = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .header()
                            .type("JWT")
                        .and()
                        .subject(ACCESS_TOKEN_SUBJECT)
                        .claim("nickname", nickname)
                        .issuedAt(now)
                        .expiration(new Date(now.getTime() + validityInMillisecondsAccess))
                        .issuer(issuer)
                        .signWith(secretKey)
                        .compact();
    }

    // Refresh 토큰 생성
    public String createRefreshToken() {
        Date now = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .header()
                            .type("JWT")
                        .and()
                        .subject(REFRESH_TOKEN_SUBJECT)
                        .issuedAt(now)
                        .expiration(new Date(now.getTime() + validityInMillisecondsRefresh))
                        .signWith(secretKey)
                        .compact();
    }

    // 토큰 검증
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
