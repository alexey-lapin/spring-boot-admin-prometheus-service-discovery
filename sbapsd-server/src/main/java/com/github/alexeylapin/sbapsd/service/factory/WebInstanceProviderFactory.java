package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.web.WebInstanceProvider;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.SneakyThrows;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.Map;

public class WebInstanceProviderFactory implements InstanceProviderFactory {

    private static final String PARAM_URL = "url";
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_INSECURE = "insecure";

    private final WebClient.Builder webClientBuilder;

    public WebInstanceProviderFactory(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public String getType() {
        return "web";
    }

    @Override
    public InstanceProvider create(ServiceProviderDef serviceProviderDef) {
        Map<String, String> params = serviceProviderDef.getParams();
        configureUrl(webClientBuilder, params);
        configureAuth(webClientBuilder, params);
        configureSsl(webClientBuilder, params);
        return new WebInstanceProvider(webClientBuilder.build());
    }

    private static void configureUrl(WebClient.Builder webClientBuilder, Map<String, String> params) {
        String url = params.get(PARAM_URL);
        webClientBuilder.baseUrl(url);
    }

    private static void configureAuth(WebClient.Builder webClientBuilder, Map<String, String> params) {
        String username = params.get(PARAM_USERNAME);
        String password = params.get(PARAM_PASSWORD);
        if (username != null && password != null) {
            webClientBuilder.filter(ExchangeFilterFunctions.basicAuthentication(username, password));
        }
    }

    @SneakyThrows
    private static void configureSsl(WebClient.Builder webClientBuilder, Map<String, String> params) {
        boolean insecure = Boolean.parseBoolean(params.get(PARAM_INSECURE));
        if (insecure) {
            SslContext sslContext = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
            HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
            webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient));
        }
    }

}
