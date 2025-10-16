package com.hzera.spring.ms.cdn.driver.api.rest.v1.providers;

import com.hzera.spring.ms.cdn.application.ports.driver.IdentityServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final IdentityServicePort identityService;

    private final CryptProvider cryptProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var rawToken = authentication.getPrincipal().toString();
        var tokenPartsOpt = processToken(rawToken);

        if (tokenPartsOpt.isEmpty()) {
            return null;
        }

        var tokenParts = tokenPartsOpt.get();
        var clientId = tokenParts[0];
        var clientSecret = tokenParts[1];

        var client = identityService.getClient(clientId);

        if (!cryptProvider.match(clientSecret, client.getClientSecretHash())) {
            return null;
        }

        return new PreAuthenticatedAuthenticationToken(authentication.getPrincipal(), client, new ArrayList<>());
    }

    private Optional<String[]> processToken(String token) {
        if (token == null || !token.contains("Basic")) {
            return Optional.empty();
        }

        String cleanedToken = token.replace("Basic ", "").trim();
        cleanedToken = new String(Base64.getDecoder().decode(cleanedToken));
        String[] parts = cleanedToken.split(":");

        if (parts.length != 2) {
            return Optional.empty();
        }

        return Optional.of(parts);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
