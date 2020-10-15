package in.avenues.springsecurity.security.filters;

import in.avenues.springsecurity.otp.OtpService;
import in.avenues.springsecurity.security.authentications.OtpAuthentication;
import in.avenues.springsecurity.security.authentications.UsernamePasswordAuthentication;
import in.avenues.springsecurity.security.util.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
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
    private OtpService otpService;
    @Autowired
    private TokenValidator tokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

        //Step 1: username and password
        //Step 2: username and cognitoId

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String otpCode = request.getHeader("otp-code");

        if (otpCode == null) {
            //Step 1:
            Authentication usernamePasswordAuthentication = new UsernamePasswordAuthentication(username, password);
            Authentication authenticatedUser = authenticationManager.authenticate(usernamePasswordAuthentication);
            if (authenticatedUser.isAuthenticated()) {
                otpService.generateOtpCode(username);
            }

        } else {
            //Step 2:
            Authentication otpAuthentication = new OtpAuthentication(username, otpCode);
            Authentication authenticatedOtp = authenticationManager.authenticate(otpAuthentication);

            if (authenticatedOtp.isAuthenticated()) {

                String token = String.valueOf(UUID.randomUUID());
                tokenValidator.add(token);
                response.setHeader("Authorization", token);
            }
        }


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equalsIgnoreCase("/login");
    }
}
