/*
 * MIT License
 *
 * Copyright (c) 2023 Alexey Lapin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.Validate;
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

    public static final String TYPE_WEB = "web";

    private static final String PARAM_URL = "url";
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_INSECURE = "insecure";

    private final WebClient.Builder webClientBuilder;

    public WebInstanceProviderFactory(WebClient.Builder webClientBuilder) {
        Validate.notNull(webClientBuilder, "webClientBuilder must not be null");
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public String getType() {
        return TYPE_WEB;
    }

    @Override
    public InstanceProvider create(ServiceProviderDef serviceProviderDef) {
        Validate.notNull(serviceProviderDef, "serviceProviderDef must not be null");
        Map<String, String> params = serviceProviderDef.getParams();
        configureUrl(webClientBuilder, params);
        configureAuth(webClientBuilder, params);
        configureSsl(webClientBuilder, params);
        return new WebInstanceProvider(webClientBuilder.build());
    }

    private static void configureUrl(WebClient.Builder webClientBuilder, Map<String, String> params) {
        String url = params.get(PARAM_URL);
        Validate.notNull(url, "server.params.url must not be null");
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
