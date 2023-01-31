package com.github.alexeylapin.sbapsd.testing.common;

import com.github.alexeylapin.sbapsd.testing.client.Client;
import com.github.alexeylapin.sbapsd.testing.server.Server;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Common {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(Common.class)
                .child(Server.class).profiles("server")
                .sibling(Client.class).profiles("client")
                .run(args);
    }

}
