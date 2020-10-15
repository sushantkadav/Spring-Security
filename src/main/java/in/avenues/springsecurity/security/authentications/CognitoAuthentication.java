package in.avenues.springsecurity.security.authentications;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CognitoAuthentication extends UsernamePasswordAuthenticationToken {

    public CognitoAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public CognitoAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}

