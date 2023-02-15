package com.github.alexeylapin.sbapsd.testing;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class V2SBATesting {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(V2SBATesting.class)
                .child(V2SBAServer.class).profiles("server")
                .sibling(V2SBAClient.class).profiles("client").properties(
                        "spring.application.name=app-1",
                        "sba.client.status=UP")
                .sibling(V2SBAClient.class).profiles("client").properties(
                        "spring.application.name=app-1",
                        "sba.client.status=DOWN")
                .sibling(V2SBAClient.class).profiles("client").properties(
                        "spring.application.name=app-2",
                        "sba.client.status=OUT_OF_SERVICE")
                .run(args);
    }

}
