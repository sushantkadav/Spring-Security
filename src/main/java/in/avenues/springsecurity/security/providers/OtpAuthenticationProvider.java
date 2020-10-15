package in.avenues.springsecurity.security.providers;

import in.avenues.springsecurity.otp.Otp;
import in.avenues.springsecurity.otp.OtpService;
import in.avenues.springsecurity.security.authentications.OtpAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    private OtpService otpService;

    public OtpAuthenticationProvider(OtpService otpService) {
        this.otpService = otpService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otpCode = String.valueOf(authentication.getCredentials());

        try {
            Otp otp = otpService.getByUsername(username);
            if (otpCode.equals(otp.getOtpCode())) {
                return new OtpAuthentication(username, otp, List.of(() -> "read"));
            }
        } catch (Exception ex) {
            System.out.println("user not found for given otp");
            ex.printStackTrace();
        }
        throw new BadCredentialsException("Error");
    }

    @Override
    public boolean supports(Class<?> authType) {
        return OtpAuthentication.class.equals(authType);
    }
}
