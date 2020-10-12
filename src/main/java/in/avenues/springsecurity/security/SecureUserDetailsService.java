package in.avenues.springsecurity.security;

import in.avenues.springsecurity.user.UserDTO;
import in.avenues.springsecurity.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecureUserDetailsService implements UserDetailsService {

    private UserService userService;

    public SecureUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDTO userDTO;
        try {
            userDTO = userService.getByUserName(username);
           return new SecureUserDetails(userDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
            //user not found.
        }
        return null;

    }
}
