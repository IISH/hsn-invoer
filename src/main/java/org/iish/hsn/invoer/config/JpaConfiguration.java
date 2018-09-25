package org.iish.hsn.invoer.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

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

    /**
     * Creates a datasource initializer which populates the database with the given resource.
     *
     * @param resource   The resource to populate the database with.
     * @param dataSource The datasource to be initialized.
     * @return A datasource initializer.
     */
    static DataSourceInitializer createDataSourceInitializer(Resource resource, DataSource dataSource) {
        if (resource.isReadable()) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
            resourceDatabasePopulator.setSqlScriptEncoding("UTF-8");
            resourceDatabasePopulator.addScript(resource);

            DataSourceInitializer initializer = new DataSourceInitializer();
            initializer.setDataSource(dataSource);
            initializer.setDatabasePopulator(resourceDatabasePopulator);

            return initializer;
        }
        return null;
    }

    /**
     * Creates the datasource based on the environment.
     *
     * @param env The environment.
     * @return A new datasource.
     */
    static DataSource createDataSource(Environment env, String name) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        if (env.acceptsProfiles("h2file")) {
            String path = (System.getProperty("database") != null)
                    ? System.getProperty("database") : System.getProperty("user.home");
            dataSourceBuilder.url("jdbc:h2:" + path + "/" + name + ";MODE=MySQL;DB_CLOSE_DELAY=-1");
        }
        return dataSourceBuilder.build();
    }
}
