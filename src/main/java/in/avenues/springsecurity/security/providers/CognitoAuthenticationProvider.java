package in.avenues.springsecurity.security.providers;

import in.avenues.springsecurity.cognito.CognitoDTO;
import in.avenues.springsecurity.cognito.CognitoService;
import in.avenues.springsecurity.security.authentications.CognitoAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CognitoAuthenticationProvider implements AuthenticationProvider {

    private CognitoService cognitoService;

    public CognitoAuthenticationProvider(CognitoService cognitoService) {
        this.cognitoService = cognitoService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String cognitoId = String.valueOf(authentication.getCredentials());

        CognitoDTO cognitoDTO = cognitoService.getCognitoByUsername(username);
        if (cognitoId.equals(cognitoDTO.getCognitoId())) {
            return new CognitoAuthentication(username, cognitoDTO, List.of(() -> "read"));
        }
        throw new BadCredentialsException("Error");
    }

    @Override
    public boolean supports(Class<?> authType) {
        return CognitoAuthentication.class.equals(authType);
    }
}
