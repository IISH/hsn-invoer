package org.iish.hsn.invoer.service.security;

import org.iish.hsn.invoer.domain.invoer.security.User;
import org.iish.hsn.invoer.repository.invoer.security.UserRepository;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides authorities for users logged in with LDAP for Spring Security to use.
 */
public class LdapAuthoritiesPopulator extends DefaultLdapAuthoritiesPopulator {
    private final UserRepository userRepository;

    /**
     * Constructor for group search scenarios. <tt>userRoleAttributes</tt> may still be set as a property.
     *
     * @param contextSource   Supplies the contexts used to search for user roles.
     * @param groupSearchBase If this is an empty string the search will be performed
     *                        from the root DN of the context factory.
     *                        If null, no search will be performed.
     * @param userRepository  The user repository.
     */
    public LdapAuthoritiesPopulator(ContextSource contextSource, String groupSearchBase,
                                    UserRepository userRepository) {
        super(contextSource, groupSearchBase);
        this.userRepository = userRepository;
    }

    /**
     * This method should be overridden if required to obtain any additional
     * roles for the given user (on top of those obtained from the standard
     * search implemented by this class).
     *
     * @param user     The context representing the user who's roles are required.
     * @param username The username.
     * @return The extra roles which will be merged with those returned by the group search.
     */
    @Override
    public Set<GrantedAuthority> getAdditionalRoles(DirContextOperations user, String username) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        User userEntity = userRepository.findByInlognaam(username);
        if (userEntity != null) {
            authorities.addAll(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        }
        return authorities;
    }
}
