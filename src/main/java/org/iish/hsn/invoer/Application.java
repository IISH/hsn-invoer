package org.iish.hsn.invoer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

// As we use two databases instead of one, we will do the JPA repositories configuration, so disable auto configuration
@SpringBootApplication(exclude = JpaRepositoriesAutoConfiguration.class)
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(Application.class);
        application.setShowBanner(false);
        application.run(args);
    }
}
