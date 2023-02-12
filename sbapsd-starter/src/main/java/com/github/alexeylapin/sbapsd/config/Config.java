package com.github.alexeylapin.sbapsd.config;

import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.InstanceProviderRegistry;
import com.github.alexeylapin.sbapsd.service.factory.CompositeInstanceProviderFactory;
import com.github.alexeylapin.sbapsd.service.factory.InstanceProviderFactory;
import com.github.alexeylapin.sbapsd.service.factory.V2ApplicationRegistryInstanceProviderFactory;
import com.github.alexeylapin.sbapsd.service.factory.V2WebInstanceProviderFactory;
import com.github.alexeylapin.sbapsd.web.InstanceController;
import com.github.alexeylapin.sbapsd.web.ReactiveHandlerMapping;
import com.github.alexeylapin.sbapsd.web.ServletHandlerMapping;
import de.codecentric.boot.admin.server.services.ApplicationRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EnableConfigurationProperties(PrometheusServiceDiscoveryProperties.class)
@Configuration(proxyBeanMethods = false)
public class Config {

    @Bean
    public InstanceProviderFactory compositeInstanceProviderFactory(List<InstanceProviderFactory> factories) {
        return new CompositeInstanceProviderFactory(factories);
    }

    @Bean
    public InstanceProviderFactory v2WebInstanceProviderFactory() {
        return new V2WebInstanceProviderFactory();
    }

    @ConditionalOnClass(ApplicationRegistry.class)
    @Configuration(proxyBeanMethods = false)
    public static class V2ApplicationRegistryConfiguration {

        @ConditionalOnBean(ApplicationRegistry.class)
        @Bean
        public InstanceProviderFactory v2ApplicationRegistryInstanceProviderFactory(ApplicationRegistry applicationRegistry) {
            return new V2ApplicationRegistryInstanceProviderFactory(applicationRegistry);
        }

    }

    @Bean
    public InstanceProviderRegistry instanceProviderRegistry(PrometheusServiceDiscoveryProperties properties,
                                                             CompositeInstanceProviderFactory instanceProviderFactory) {
        Map<String, InstanceProviderDef> servers = properties.getServers();
        ConcurrentHashMap<String, InstanceProvider> map = new ConcurrentHashMap<>();
        for (Map.Entry<String, InstanceProviderDef> entry : servers.entrySet()) {
            InstanceProvider instanceProvider = instanceProviderFactory.create(entry.getValue());
            map.put(entry.getKey(), instanceProvider);
        }
        return new InstanceProviderRegistry(map);
    }

    @Bean
    public InstanceController instanceController(InstanceProviderRegistry InstanceProviderRegistry) {
        return new InstanceController(InstanceProviderRegistry);
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public static class ReactiveRestApiConfiguration {

        @Bean
        public ReactiveHandlerMapping reactiveHandlerMapping(RequestedContentTypeResolver webFluxContentTypeResolver) {
            ReactiveHandlerMapping mapping = new ReactiveHandlerMapping();
            mapping.setOrder(0);
            mapping.setContentTypeResolver(webFluxContentTypeResolver);
            return mapping;
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @AutoConfigureAfter(WebMvcAutoConfiguration.class)
    public static class ServletRestApiConfiguration {

        @Bean
        public ServletHandlerMapping servletHandlerMapping(ContentNegotiationManager contentNegotiationManager) {
            ServletHandlerMapping mapping = new ServletHandlerMapping();
            mapping.setOrder(0);
            mapping.setContentNegotiationManager(contentNegotiationManager);
            return mapping;
        }

    }

}
