package org.iish.hsn.invoer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import static org.springframework.boot.Banner.Mode;

// As we use two databases instead of one, we will do the JPA repositories configuration, so disable auto configuration
@SpringBootApplication(exclude = JpaRepositoriesAutoConfiguration.class)
public class Application extends SpringBootServletInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    static {
        try {
            System.loadLibrary("turbojpeg");
        }
        catch (UnsatisfiedLinkError ule) {
            LOGGER.warn("Native library libturbojpeg not found; using ImageIO instead to read scans!");
        }
    }

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
