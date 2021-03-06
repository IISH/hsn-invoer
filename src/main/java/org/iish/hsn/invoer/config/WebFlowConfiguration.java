package org.iish.hsn.invoer.config;

import org.iish.hsn.invoer.flow.AkteFlowExecutionListener;
import org.iish.hsn.invoer.util.InputMetadata;
import org.iish.hsn.invoer.util.InputMetadataChecker;
import org.iish.hsn.invoer.util.InputMetadataInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

import java.util.List;

@Configuration
public class WebFlowConfiguration extends AbstractFlowConfiguration {
    @Autowired
    private Environment env;

    @Autowired
    private InputMetadata inputMetadata;

    @Autowired
    private InputMetadataChecker inputMetadataChecker;

    @Autowired
    private ConversionService mvcConversionService;

    @Autowired
    private List<ViewResolver> viewResolvers;

    @Bean
    public FlowHandlerMapping flowHandlerMapping() {
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setOrder(-1);
        handlerMapping.setFlowRegistry(flowRegistry());
        handlerMapping.setInterceptors(new InputMetadataInterceptor(this.inputMetadata, this.inputMetadataChecker));
        return handlerMapping;
    }

    @Bean
    public FlowHandlerAdapter flowHandlerAdapter() {
        FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
        handlerAdapter.setFlowExecutor(flowExecutor());
        handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return handlerAdapter;
    }

    @Bean
    public FlowExecutor flowExecutor() {
        // There is no going back to a previous screen, unless we're running with the development profile
        int maxFlowExecutionSnapshots = this.env.acceptsProfiles(Profiles.of("development")) ? -1 : 0;
        return getFlowExecutorBuilder(flowRegistry())
                .setMaxFlowExecutionSnapshots(maxFlowExecutionSnapshots)
                .addFlowExecutionListener(new AkteFlowExecutionListener()).build();
    }

    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return getFlowDefinitionRegistryBuilder(flowBuilderServices())
                .setBasePath("classpath*:templates")
                .addFlowLocationPattern("/**/*-flow.xml")
                .build();
    }

    @Bean
    public FlowBuilderServices flowBuilderServices() {
        return getFlowBuilderServicesBuilder()
                .setViewFactoryCreator(mvcViewFactoryCreator())
                .setDevelopmentMode(this.env.acceptsProfiles(Profiles.of("development")))
                .setConversionService(new DefaultConversionService(this.mvcConversionService))
                .build();
    }

    @Bean
    public MvcViewFactoryCreator mvcViewFactoryCreator() {
        MvcViewFactoryCreator mvcViewFactoryCreator = new MvcViewFactoryCreator();
        mvcViewFactoryCreator.setViewResolvers(this.viewResolvers);
        mvcViewFactoryCreator.setUseSpringBeanBinding(true);
        return mvcViewFactoryCreator;
    }
}
