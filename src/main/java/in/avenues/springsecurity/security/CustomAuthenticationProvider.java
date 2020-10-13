package in.avenues.springsecurity.security;

import in.avenues.springsecurity.user.UserDTO;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private SecureUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(SecureUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username, password, user.getAuthorities());
                return authenticationToken;
            }else {
                System.out.println("incorrect password");
                throw new BadCredentialsException("Incorrect password");
            }
        }
        throw new BadCredentialsException("Error!");
    }

    @Override
    public boolean supports(Class<?> authType) {
        return UsernamePasswordAuthenticationToken.class.equals(authType);
    }
}
