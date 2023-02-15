package com.github.alexeylapin.sbapsd.testing;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class V1SBATesting {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(V1SBATesting.class)
                .child(V1SBAServer.class).profiles("server")
                .sibling(V1SBAClient.class).profiles("client").properties(
                        "spring.application.name=app-1",
                        "sba.client.status=UP")
                .sibling(V1SBAClient.class).profiles("client").properties(
                        "spring.application.name=app-1",
                        "sba.client.status=DOWN")
                .sibling(V1SBAClient.class).profiles("client").properties(
                        "spring.application.name=app-2",
                        "sba.client.status=OUT_OF_SERVICE")
                .run(args);
    }

}
