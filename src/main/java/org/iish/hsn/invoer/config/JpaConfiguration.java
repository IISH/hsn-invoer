package org.iish.hsn.invoer.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class JpaConfiguration {

    /**
     * If the H2 database is used, (for development purposes) the H2 console can be reached by navigating to /console
     */
    @Bean
    @Profile("h2")
    public ServletRegistrationBean h2ConsoleServletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}
