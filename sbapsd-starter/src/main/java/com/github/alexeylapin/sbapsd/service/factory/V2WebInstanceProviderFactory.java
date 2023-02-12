package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.InstanceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.v2.V2WebInstanceProvider;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public class V2WebInstanceProviderFactory implements InstanceProviderFactory {

    @Override
    public String getType() {
        return "web-v2";
    }

    @Override
    public InstanceProvider create(InstanceProviderDef instanceProviderDef) {
        Map<String, String> params = instanceProviderDef.getParams();
        String url = params.get("url");
        WebClient.Builder builder = WebClient.builder().baseUrl(url);
        String username = params.get("username");
        String password = params.get("password");
        if (username != null && password != null) {
            builder.filter(ExchangeFilterFunctions.basicAuthentication(username, password));
        }
        return new V2WebInstanceProvider(item -> true, instanceProviderDef.getLabels(), builder.build());
    }

}
