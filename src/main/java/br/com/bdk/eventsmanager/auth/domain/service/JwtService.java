package br.com.bdk.eventsmanager.auth.domain.service;

import br.com.bdk.eventsmanager.auth.config.AuthJwtProperties;
import br.com.bdk.eventsmanager.auth.domain.model.UserDetailsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String USER_ID_CLAIM_NAME = "userId";
    private static final String COMPANY_ID_CLAIM_NAME = "companyId";
    private static final String IS_ADMIN_CLAIM_NAME = "isAdmin";

    private final AuthJwtProperties properties;

    public String extractExternalUserId(String token) {
        return extractClaim(token, claims -> claims.get(USER_ID_CLAIM_NAME, String.class));
    }

    public boolean isValid(String token, UserDetails user) {
        return user.isEnabled()
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(UserDetailsDto user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + properties.getExpirationInSeconds() * DateUtils.MILLIS_PER_SECOND))
                .signWith(getSigningKey())
                .claims()
                    .add(USER_ID_CLAIM_NAME, user.getExternalId())
                    .add(COMPANY_ID_CLAIM_NAME, user.getCompanyExternalId())
                    .add(IS_ADMIN_CLAIM_NAME, user.isAdmin())
                .and()
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(properties.getSigningKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
