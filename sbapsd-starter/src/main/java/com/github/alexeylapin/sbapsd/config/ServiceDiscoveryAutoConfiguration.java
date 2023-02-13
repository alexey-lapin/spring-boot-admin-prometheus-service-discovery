package com.github.alexeylapin.sbapsd.config;

import com.github.alexeylapin.sbapsd.config.def.InstanceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.InstanceProviderRegistry;
import com.github.alexeylapin.sbapsd.service.factory.CompositeInstanceProviderFactory;
import com.github.alexeylapin.sbapsd.service.factory.InstanceProviderFactory;
import com.github.alexeylapin.sbapsd.service.factory.V2ApplicationRegistryInstanceProviderFactory;
import com.github.alexeylapin.sbapsd.service.factory.V2WebInstanceProviderFactory;
import com.github.alexeylapin.sbapsd.web.ServiceDiscoveryController;
import com.github.alexeylapin.sbapsd.web.ReactiveHandlerMapping;
import com.github.alexeylapin.sbapsd.web.ServletHandlerMapping;
import de.codecentric.boot.admin.server.config.AdminServerAutoConfiguration;
import de.codecentric.boot.admin.server.services.ApplicationRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

@ConditionalOnBean(ServiceDiscoveryMarkerConfiguration.Marker.class)
@AutoConfigureAfter(AdminServerAutoConfiguration.class)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ServiceDiscoveryProperties.class)
public class ServiceDiscoveryAutoConfiguration {

    @ConditionalOnClass(ApplicationRegistry.class)
    @Configuration(proxyBeanMethods = false)
    public static class V2ApplicationRegistryConfiguration {

        @ConditionalOnBean(ApplicationRegistry.class)
        @ConditionalOnMissingBean
        @Bean
        public V2ApplicationRegistryInstanceProviderFactory v2ApplicationRegistryInstanceProviderFactory(
                ApplicationRegistry applicationRegistry) {
            return new V2ApplicationRegistryInstanceProviderFactory(applicationRegistry);
        }

    }

    @ConditionalOnMissingBean
    @Bean
    public V2WebInstanceProviderFactory v2WebInstanceProviderFactory() {
        return new V2WebInstanceProviderFactory();
    }

    @ConditionalOnMissingBean
    @Bean
    public CompositeInstanceProviderFactory compositeInstanceProviderFactory(List<InstanceProviderFactory> factories) {
        return new CompositeInstanceProviderFactory(factories);
    }

    @ConditionalOnMissingBean
    @Bean
    public InstanceProviderRegistry instanceProviderRegistry(ServiceDiscoveryProperties properties,
                                                             CompositeInstanceProviderFactory instanceProviderFactory) {
        Map<String, InstanceProvider> map = new ConcurrentHashMap<>();
        for (Map.Entry<String, InstanceProviderDef> entry : properties.getServers().entrySet()) {
            InstanceProvider instanceProvider = instanceProviderFactory.create(entry.getValue());
            map.put(entry.getKey(), instanceProvider);
        }
        return new InstanceProviderRegistry(map);
    }

    @ConditionalOnMissingBean
    @Bean
    public ServiceDiscoveryController instanceController(InstanceProviderRegistry InstanceProviderRegistry) {
        return new ServiceDiscoveryController(InstanceProviderRegistry);
    }

    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @Configuration(proxyBeanMethods = false)
    public static class ReactiveRestApiConfiguration {

        @Bean
        public ReactiveHandlerMapping reactiveHandlerMapping(RequestedContentTypeResolver webFluxContentTypeResolver) {
            ReactiveHandlerMapping mapping = new ReactiveHandlerMapping();
            mapping.setOrder(0);
            mapping.setContentTypeResolver(webFluxContentTypeResolver);
            return mapping;
        }

    }

    @AutoConfigureAfter(WebMvcAutoConfiguration.class)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @Configuration(proxyBeanMethods = false)
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
