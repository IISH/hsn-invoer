package org.iish.hsn.invoer.config;

import org.iish.hsn.invoer.repository.invoer.security.UserRepository;
import org.iish.hsn.invoer.service.security.HsnUserDetailsService;
import org.iish.hsn.invoer.service.security.LdapAuthoritiesPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired private Environment           env;
    @Autowired private UserRepository        userRepository;
    @Autowired private HsnUserDetailsService userDetailsService;

    @Value("${ldap.url:ldap://}") private               String ldapUrl;
    @Value("${ldap.manager.dn:cn=}") private            String ldapManagerDn;
    @Value("${ldap.manager.password:password}") private String ldapManagerPassword;
    @Value("${ldap.search.base:ou=}") private           String ldapSearchBase;
    @Value("${ldap.search.filter:cn=}") private         String ldapSearchFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().and()
                // Disable Cross-Site Request Forgery token
                .csrf().disable()
                // Disable HTTP Basic authentication
                .httpBasic().disable();

        // If we are running H2 in development mode, then make sure we can always reach the H2 console
        if (this.env.acceptsProfiles(Profiles.of("development")) && this.env.acceptsProfiles(Profiles.of("h2"))) {
            httpSecurity
                    .authorizeRequests()
                        .antMatchers("/console/**").permitAll()
                        .and()
                    .headers()
                        .frameOptions().disable();
        }
        // If an auth profile has been chosen, close all other requests: the user needs to be authenticated first
        else if (this.env.acceptsProfiles(Profiles.of("ldapAuth", "dbAuth"))) {
            httpSecurity
                    // What is the login page
                    .formLogin()
                        .loginPage("/login")
                        .permitAll()
                        .and()
                    // What is the logout page
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout/success")
                        .permitAll()
                        .and()
                    // Any request needs authentication first (except static resources)
                    .authorizeRequests()
                        .antMatchers("/css/**", "/fonts/**", "/js/**", "/favicon.ico").permitAll()
                        .anyRequest().authenticated();
        }
        // Otherwise just accept any request
        else
            httpSecurity.authorizeRequests().anyRequest().permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        if (this.env.acceptsProfiles(Profiles.of("ldapAuth"))) {
            DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(this.ldapUrl);
            contextSource.setUserDn(this.ldapManagerDn);
            contextSource.setPassword(this.ldapManagerPassword);
            contextSource.afterPropertiesSet();

            LdapAuthoritiesPopulator ldapAuthoritiesPopulator =
                    new LdapAuthoritiesPopulator(contextSource, this.ldapSearchBase, this.userRepository);

            authenticationManagerBuilder
                    .ldapAuthentication()
                    .contextSource(contextSource)
                    .userSearchBase(this.ldapSearchBase)
                    .userSearchFilter(this.ldapSearchFilter)
                    .ldapAuthoritiesPopulator(ldapAuthoritiesPopulator);
        }

        if (this.env.acceptsProfiles(Profiles.of("dbAuth"))) {
            authenticationManagerBuilder
                    .userDetailsService(this.userDetailsService)
                    .passwordEncoder(new BCryptPasswordEncoder(10));
        }
    }
}