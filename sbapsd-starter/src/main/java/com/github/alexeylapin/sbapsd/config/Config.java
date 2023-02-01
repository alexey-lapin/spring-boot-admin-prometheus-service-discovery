package com.github.alexeylapin.sbapsd.config;

import com.github.alexeylapin.sbapsd.InstanceProvider;
import com.github.alexeylapin.sbapsd.ServerAccessorRegistry;
import com.github.alexeylapin.sbapsd.WebInstanceProvider;
import com.github.alexeylapin.sbapsd.web.InstanceController;
import com.github.alexeylapin.sbapsd.web.ReactiveHandlerMapping;
import com.github.alexeylapin.sbapsd.web.ServletHandlerMapping;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.function.client.WebClient;

@EnableConfigurationProperties(PrometheusServiceDiscoveryProperties.class)
@Configuration(proxyBeanMethods = false)
public class Config {

    @Bean
    public ServerAccessorRegistry serverAccessorRegistry(PrometheusServiceDiscoveryProperties properties) {
        return new ServerAccessorRegistry(properties.getServers());
    }

    @Bean
    public InstanceProvider instanceProvider() {
        WebClient webClient = WebClient.create("http://localhost:8080");
        return new WebInstanceProvider(webClient);
    }

    @Bean
    public InstanceController instanceController(InstanceProvider instanceProvider) {
        return new InstanceController(instanceProvider);
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
