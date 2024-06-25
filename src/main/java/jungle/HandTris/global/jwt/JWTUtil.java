package jungle.HandTris.global.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jungle.HandTris.domain.exception.InvalidTokenFormatException;
import jungle.HandTris.domain.exception.TokenNotProvideException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${spring.jwt.access.header}")
    public String accessHeader;

    @Value("${spring.jwt.access.subject}")
    private String accessSubject;

    @Value("${spring.jwt.access.expiration}")
    private long validityInMillisecondsAccess;

    @Value("${spring.jwt.refresh.header}")
    public String refreshHeader;

    @Value("${spring.jwt.refresh.subject}")
    private String refreshSubject;

    @Value("${spring.jwt.refresh.expiration}")
    private long validityInMillisecondsRefresh;

    @Value("${spring.jwt.issuer}")
    private String issuer;

    @Value("${spring.jwt.bearer}")
    private String bearerPrefix;

    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // header Access 토큰을 가져오기
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(accessHeader);
        if (StringUtils.hasText(bearerToken)) {
            if (bearerToken.startsWith(bearerPrefix)) {
                return bearerToken.substring(7);
            }
            throw new InvalidTokenFormatException();
        }
        throw new TokenNotProvideException();
    }

    // header Refresh 토큰을 가져오기
    public String resolveRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(refreshHeader);
        if (StringUtils.hasText(bearerToken)) {
            if (bearerToken.startsWith(bearerPrefix)) {
                return bearerToken.substring(7);
            }
            throw new InvalidTokenFormatException();
        }
        throw new TokenNotProvideException();
    }

    // Access 토큰 생성
    public String createAccessToken(String nickname) {
        Date now = new Date();

        return bearerPrefix + " " +
                Jwts.builder()
                        .header()
                            .type("JWT")
                        .and()
                        .subject(accessSubject)
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

        return bearerPrefix +" " +
                Jwts.builder()
                        .header()
                            .type("JWT")
                        .and()
                        .subject(refreshSubject)
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
