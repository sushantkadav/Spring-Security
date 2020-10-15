package in.avenues.springsecurity.security.filters;

import in.avenues.springsecurity.security.validator.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationProvider extends OncePerRequestFilter {

    @Autowired
    private TokenValidator tokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
            String token = request.getHeader("Authorization");


            boolean exists = tokenValidator.contains(token);

            if (exists){
                chain.doFilter(request, response);
            }else {
                throw new BadCredentialsException("Invalid token");
            }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equalsIgnoreCase("/login");
    }
}
