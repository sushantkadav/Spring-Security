package in.avenues.springsecurity.security.filters;

import in.avenues.springsecurity.cognito.CognitoService;
import in.avenues.springsecurity.security.authentications.CognitoAuthentication;
import in.avenues.springsecurity.security.authentications.UsernamePasswordAuthentication;
import in.avenues.springsecurity.security.validator.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class UsernamePasswordAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CognitoService cognitoService;
    @Autowired
    private TokenValidator tokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

        //Step 1: username and password
        //Step 2: username and cognitoId

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String cognitoId = request.getHeader("cognitoId");

        if (cognitoId == null) {
            //Step 1:
            Authentication usernamePasswordAuthentication = new UsernamePasswordAuthentication(username, password);
            Authentication authenticatedUser = authenticationManager.authenticate(usernamePasswordAuthentication);
            if (authenticatedUser.isAuthenticated()) {
                cognitoService.generateCognitoId(username);
            }

        } else {
            //Step 2:
            Authentication cognitoAuthentication = new CognitoAuthentication(username, cognitoId);
            Authentication authenticatedUser = authenticationManager.authenticate(cognitoAuthentication);
            String token = String.valueOf(UUID.randomUUID());
            tokenValidator.add(token);
            if (authenticatedUser.isAuthenticated()) {
                response.setHeader("Authorization", token);
            }
        }


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equalsIgnoreCase("/login");
    }
}
