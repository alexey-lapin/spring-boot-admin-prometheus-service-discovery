package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.v1.V1WebInstanceProvider;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public class V1WebInstanceProviderFactory implements InstanceProviderFactory {

    @Override
    public String getType() {
        return "web-v1";
    }

    @Override
    public InstanceProvider create(ServiceProviderDef serviceProviderDef) {
        Map<String, String> params = serviceProviderDef.getParams();
        String url = params.get("url");
        WebClient.Builder builder = WebClient.builder().baseUrl(url);
        String username = params.get("username");
        String password = params.get("password");
        if (username != null && password != null) {
            builder.filter(ExchangeFilterFunctions.basicAuthentication(username, password));
        }
        return new V1WebInstanceProvider(builder.build());
    }

}
