package in.avenues.springsecurity.security.providers;

import in.avenues.springsecurity.security.authentications.TokenAuthentication;
import in.avenues.springsecurity.security.util.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private TokenValidator tokenValidator;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        boolean exists = tokenValidator.contains(token);
        if (exists) {
            return new TokenAuthentication(token, null, null);
        }
        throw new BadCredentialsException("Unauthenticated token");
    }

    @Override
    public boolean supports(Class<?> authType) {
        return TokenAuthentication.class.equals(authType);
    }
}
