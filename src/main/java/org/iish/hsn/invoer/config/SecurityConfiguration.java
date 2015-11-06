package org.iish.hsn.invoer.config;

import org.iish.hsn.invoer.service.security.HsnUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired private Environment           env;
    @Autowired private HsnUserDetailsService userDetailsService;

    @Value("${ldap.url:ldap://}") private               String ldapUrl;
    @Value("${ldap.manager.dn:cn=}") private            String ldapManagerDn;
    @Value("${ldap.manager.password:password}") private String ldapManagerPassword;
    @Value("${ldap.search.base:ou=}") private           String ldapSearchBase;
    @Value("${ldap.search.filter:cn=}") private         String ldapSearchFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // All pages, except /css/**, /fonts/** and /js/**, require authorization first
                .authorizeRequests()
                    .antMatchers("/css/**", "/fonts/**", "/js/**").permitAll()
                    .and()
                // Disable Cross-Site Request Forgery token
                .csrf().disable()
                // Disable HTTP Basic authentication
                .httpBasic().disable()
                // What is our login/logout page?
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();

        // If we are running H2 in development mode, then make sure we can always reach the H2 console
        if (this.env.acceptsProfiles("development") && this.env.acceptsProfiles("h2")) {
            httpSecurity
                    .authorizeRequests()
                        .antMatchers("/console/**").permitAll()
                        .and()
                    .headers()
                        .frameOptions().disable();
        }

        // If an auth profile has been chosen, close all other requests: the user needs to be authenticated first
        if (this.env.acceptsProfiles("ldapAuth", "dbAuth")) {
            httpSecurity.authorizeRequests().anyRequest().authenticated();
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        if (this.env.acceptsProfiles("ldapAuth")) {
            authenticationManagerBuilder
                    .ldapAuthentication()
                        .contextSource()
                            .url(this.ldapUrl)
                            .managerDn(this.ldapManagerDn)
                            .managerPassword(this.ldapManagerPassword)
                            .and()
                        .userSearchBase(this.ldapSearchBase)
                        .userSearchFilter(this.ldapSearchFilter);
        }

        if (this.env.acceptsProfiles("dbAuth")) {
            authenticationManagerBuilder
                    .userDetailsService(this.userDetailsService)
                        .passwordEncoder(new BCryptPasswordEncoder(10));
        }
    }
}