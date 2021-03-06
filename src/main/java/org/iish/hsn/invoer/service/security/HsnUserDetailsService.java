package org.iish.hsn.invoer.service.security;

import org.iish.hsn.invoer.domain.invoer.security.User;
import org.iish.hsn.invoer.repository.invoer.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Provides user details from the database for Spring Security to use.
 */
@Service
public class HsnUserDetailsService implements UserDetailsService {
    @Autowired private UserRepository userRepository;

    /**
     * Locates the user based on the username. In the actual implementation, the search may possibly be case
     * sensitive, or case insensitive depending on how the implementation instance is configured. In this case, the
     * <code>UserDetails</code> object that comes back may have a username that is of a different case than what was
     * actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByInlognaam(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found!");
        }

        return new HsnUserDetails(user.getInlognaam(), user.getWachtwoord());
    }
}
