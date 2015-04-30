package org.iish.hsn.invoer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "org.iish.hsn.invoer.repository.reference",
                       entityManagerFactoryRef = "referenceEntityManagerFactory")
public class ReferenceDataSourceConfiguration {
    @Autowired private JpaProperties properties;

    @Value("classpath:data/reference/data.sql") private Resource dataScript;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.reference")
    public DataSource referenceDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean referenceEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        DataSource dataSource = this.referenceDataSource();
        Map<String, String> properties = this.properties.getHibernateProperties(dataSource);

        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("org.iish.hsn.invoer.domain.reference")
                .persistenceUnit("reference")
                .build();
    }

    /*@PostConstruct
        public void databasePopulator() {
            final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(this.dataScript);
            populator.execute(this.referenceDataSource());
    }*/
}
