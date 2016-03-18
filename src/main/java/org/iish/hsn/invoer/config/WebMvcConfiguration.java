package org.iish.hsn.invoer.config;

import org.iish.hsn.invoer.thymeleaf.HSNThymeleafDialect;
import org.iish.hsn.invoer.util.CachingInterceptor;
import org.iish.hsn.invoer.util.InputMetadata;
import org.iish.hsn.invoer.util.InputMetadataInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.AjaxThymeleafViewResolver;
import org.thymeleaf.spring4.view.FlowAjaxThymeleafView;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
    @Value("${random-to-static-source:false}")
    private boolean randomToStaticSource;

    @Autowired
    private InputMetadata inputMetadata;

    @Autowired
    private ThymeleafProperties thymeleafProperties;

    @Autowired
    private final Collection<ITemplateResolver> templateResolvers = Collections.emptySet();

    @Autowired(required = false)
    private final Collection<IDialect> dialects = Collections.emptySet();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InputMetadataInterceptor(this.inputMetadata));
        registry.addInterceptor(new CachingInterceptor());
    }

    /**
     * Override of the templateEngine bean created by Spring Boot.
     * Adds an extra dialect for additional functionality.
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        for (ITemplateResolver templateResolver : this.templateResolvers) {
            engine.addTemplateResolver(templateResolver);
        }
        for (IDialect dialect : this.dialects) {
            engine.addDialect(dialect);
        }
        engine.addDialect(new HSNThymeleafDialect(randomToStaticSource));
        return engine;
    }

    /**
     * Override of the thymeleafViewResolver bean created by Spring Boot.
     * Returns an AjaxThymeleafViewResolver in order to enable AJAX support with Spring Web Flow.
     */
    @Bean
    public AjaxThymeleafViewResolver thymeleafViewResolver() {
        AjaxThymeleafViewResolver resolver = new AjaxThymeleafViewResolver();
        resolver.setViewClass(FlowAjaxThymeleafView.class);
        resolver.setTemplateEngine(this.templateEngine());
        resolver.setCharacterEncoding(this.thymeleafProperties.getEncoding());
        resolver.setContentType(appendCharset(this.thymeleafProperties.getContentType(),
                resolver.getCharacterEncoding()));
        resolver.setExcludedViewNames(this.thymeleafProperties.getExcludedViewNames());
        resolver.setViewNames(this.thymeleafProperties.getViewNames());
        return resolver;
    }

    /**
     * Copied from the ThymeleafViewResolverConfiguration class in Spring Boot for use with the thymeleafViewResolver.
     *
     * @see org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.ThymeleafViewResolverConfiguration
     */
    private String appendCharset(String type, String charset) {
        if (type.contains("charset=")) {
            return type;
        }
        return type + ";charset=" + charset;
    }
}
