package org.iish.hsn.invoer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import static org.springframework.boot.Banner.Mode;

// As we use two databases instead of one, we will do the JPA repositories configuration, so disable auto configuration
@SpringBootApplication(exclude = JpaRepositoriesAutoConfiguration.class)
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBannerMode(Mode.OFF);
        application.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application
                .sources(Application.class)
                .bannerMode(Mode.OFF);
    }
}
