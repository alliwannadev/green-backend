package alliwannadev.shop.common.security;

import alliwannadev.shop.common.properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "authorities";
    private static final String USER_ID_KEY = "userId";

    private final JwtProperties jwtProperties;
    private final CustomUserDetailsService customUserDetailsService;

    public String generateToken(
            CustomUser customUser,
            Date tokenExpiration,
            TokenType tokenType
    ) {
        return createToken(customUser, tokenExpiration, getSecret(tokenType));
    }

    public boolean validateToken(String token, TokenType tokenType) {
        try {
            byte[] secret = getSecret(tokenType);
            getClaims(token, secret);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못 되었습니다.");
        }

        return false;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(getEmail(token, TokenType.ACCESS_TOKEN));
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    public Claims parseToken(String token, TokenType tokenType) {
        byte[] secret = getSecret(tokenType);
        return getClaims(token, secret);
    }

    public Long getUserId(String token, TokenType tokenType) {
        Claims claims = parseToken(token, tokenType);
        return claims.get(USER_ID_KEY, Long.class);
    }

    public String getEmail(String token, TokenType tokenType) {
        Claims claims = parseToken(token, tokenType);
        return claims.getSubject();
    }

    private Key getSigningKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
    }

    private String createToken(
            CustomUser customUser,
            Date tokenExpiration,
            byte[] secretKey
    ) {
        String authorities = customUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String token = Jwts.builder()
                .setSubject(customUser.getEmail())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(USER_ID_KEY, Long.parseLong(customUser.getUsername()))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .setExpiration(tokenExpiration)
                .compact();

        return token;
    }

    private Claims getClaims(String token, byte[] secretKey) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private byte[] getSecret(TokenType tokenType) {
        byte[] secret;
        switch (tokenType) {
            case ACCESS_TOKEN -> {
                String accessSecret = jwtProperties.getAccessSecret();
                secret = accessSecret.getBytes(StandardCharsets.UTF_8);
            }
            default -> throw new RuntimeException("잘못된 토큰 타입입니다.");
        }

        return secret;
    }
}
