package library.services.currentuser;

import library.domain.CurrentUser;
import library.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CurrentUserDetailsService userDetailsService;

    @Override
    public boolean canAccessUser(CurrentUser currentUser, int userId) {
        LOGGER.debug("Checking if user={} has access to user={}", currentUser, userId);
        return currentUser != null
                && (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getId() == userId);
    }

    @Override
    public void autologin(String username, String password) {


        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        System.out.println("userDetails = " + userDetails.getUsername());
        try
        {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (AuthenticationException e)
        {
            System.out.println("authenticate failed");
        }

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            System.out.println(String.format("Auto login %s successfully!", username));
            return;
        }

        System.out.println("Failed to login hehe");
    }

}
