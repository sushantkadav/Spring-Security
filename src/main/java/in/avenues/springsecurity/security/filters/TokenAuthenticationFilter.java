package in.avenues.springsecurity.security.filters;

import in.avenues.springsecurity.security.authentications.TokenAuthentication;
import in.avenues.springsecurity.security.util.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        Authentication tokenAuthentication = new TokenAuthentication(token, null);
        Authentication authenticatedToken = authenticationManager.authenticate(tokenAuthentication);


        if (authenticatedToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
                chain.doFilter(request, response);
            }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equalsIgnoreCase("/login");
    }
}
