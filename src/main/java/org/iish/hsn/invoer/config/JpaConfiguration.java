package org.iish.hsn.invoer.config;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceInitializedEvent;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class JpaConfiguration {
    @Autowired
    @Qualifier("invoerDataSource")
    private DataSource invoerDataSource;

    @Autowired
    @Qualifier("referenceDataSource")
    private DataSource referenceDataSource;

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

    @Bean
    public DataSourceInit dataSourceInit() {
        return new DataSourceInit(this.invoerDataSource, this.referenceDataSource);
    }

    class DataSourceInit implements ApplicationListener<DataSourceInitializedEvent> {
        private boolean initStarted = false;

        private DataSource invoerDataSource;
        private DataSource referenceDataSource;

        public DataSourceInit(DataSource invoerDataSource, DataSource referenceDataSource) {
            this.invoerDataSource = invoerDataSource;
            this.referenceDataSource = referenceDataSource;
        }

        @Override
        public void onApplicationEvent(DataSourceInitializedEvent event) {
            if (!this.initStarted) {
                this.initStarted = true;

                runWith(this.invoerDataSource, new ClassPathResource("data/invoer.sql"));
                runWith(this.referenceDataSource, new ClassPathResource("data/reference.sql"));
            }
        }

        private void runWith(DataSource dataSource, Resource resource) {
            if (resource.isReadable()) {
                ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
                resourceDatabasePopulator.setSqlScriptEncoding("UTF-8");
                resourceDatabasePopulator.addScript(resource);
                resourceDatabasePopulator.execute(dataSource);
            }
        }
    }
}
