package com.github.alexeylapin.sbapsd.testing;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class V3SBATesting {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(V3SBATesting.class)
                .child(V3SBAServer.class).profiles("server")
                .sibling(V3SBAClient.class).profiles("client").properties(
                        "spring.application.name=app-1",
                        "sba.client.status=UP")
                .sibling(V3SBAClient.class).profiles("client").properties(
                        "spring.application.name=app-1",
                        "sba.client.status=DOWN")
                .sibling(V3SBAClient.class).profiles("client").properties(
                        "spring.application.name=app-2",
                        "sba.client.status=OUT_OF_SERVICE")
                .sibling(V3SBAClient.class).profiles("client").properties(
                        "spring.application.name=app-3",
                        "sba.client.status=UP")
                .run(args);
    }

}
