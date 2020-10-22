package org.iish.hsn.invoer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "org.iish.hsn.invoer.repository.invoer",
                       entityManagerFactoryRef = "invoerEntityManagerFactory")
public class InvoerDataSourceConfiguration {
    @Autowired private Environment env;
    @Autowired private JpaProperties jpaProperties;
    @Autowired private HibernateProperties hibernateProperties;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.invoer")
    public DataSource invoerDataSource() {
        return JpaConfiguration.createDataSource(this.env, "invoer");
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean invoerEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        DataSource dataSource = this.invoerDataSource();
        Map<String, Object> properties = this.hibernateProperties.determineHibernateProperties(
                this.jpaProperties.getProperties(), new HibernateSettings());

        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("org.iish.hsn.invoer.domain.invoer")
                .persistenceUnit("invoer")
                .build();
    }

    @Bean
    public DataSourceInitializer invoerDataSourceInitializer(
            @Qualifier("invoerDataSource") DataSource invoerDataSource) {
        Resource resource = new ClassPathResource("data/invoer.sql");
        return JpaConfiguration.createDataSourceInitializer(resource, invoerDataSource);
    }
}
