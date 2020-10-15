package in.avenues.springsecurity.security.providers;

import in.avenues.springsecurity.security.models.SecureUserDetailsService;
import in.avenues.springsecurity.security.authentications.UsernamePasswordAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private SecureUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public UsernamePasswordAuthenticationProvider(SecureUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
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
                return new UsernamePasswordAuthentication(username, password, user.getAuthorities());
            }else {
                System.out.println("incorrect user or password");
                throw new BadCredentialsException("Incorrect user or password");
            }
        }
        throw new BadCredentialsException("Error!");
    }

    @Override
    public boolean supports(Class<?> authType) {
        return UsernamePasswordAuthentication.class.equals(authType);
    }
}
