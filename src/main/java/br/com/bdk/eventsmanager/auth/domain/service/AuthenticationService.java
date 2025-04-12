package br.com.bdk.eventsmanager.auth.domain.service;

import br.com.bdk.eventsmanager.auth.domain.model.UserDetailsDto;
import br.com.bdk.eventsmanager.auth.domain.model.dto.TokenDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @NonNull
    public TokenDto authenticate(@NonNull String username, @NonNull String password) {
        final var userPasswordAuthenticationToken = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        final var user = (UserDetailsDto) userPasswordAuthenticationToken.getPrincipal();
        final var token = jwtService.generateToken(user);
        final var expirationDate = jwtService.extractClaim(token, Claims::getExpiration);

        SecurityContextHolder.getContext().setAuthentication(userPasswordAuthenticationToken);
        return TokenDto.builder()
                .accessToken(token)
                .expiresIn((expirationDate.getTime() - System.currentTimeMillis()) / 1000)
                .build();
    }
}
