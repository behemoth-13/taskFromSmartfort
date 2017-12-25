package org.smartfort.test.task.controller;

import io.netty.channel.Channel;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Examples:
 * curl -i -H "Content-Type: application/json" -X POST -d '{"id":0,"firstName":"Alex","lastName":"Alexeev","email":"alex@yandex.ru","dateOfBirth":"27.08.1999"}' http://localhost:8080/user
 * curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/user/1
 * curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/user
 * curl -i -H "Content-Type: application/json" -X PUT -d '{"id":0,"firstName":"Ivan","lastName":"Petrov","email":"notalex@yandex.ru","dateOfBirth":"27.08.1989"}' http://localhost:8080/user
 * curl -i -X DELETE http://localhost:8080/user/1
 */

public class App {

    public static final String ROOT_PATH = "user";

    private static final URI BASE_URI = URI.create("http://localhost:8080/");

    public static void main(String[] args) {
        try {
            System.out.println("Test task for smartfort from Afanasyeu Alexei");

            ResourceConfig resourceConfig = new ResourceConfig();
            resourceConfig.register(JacksonConfigurator.class).register(ControllerConfig.class);
            final Channel server = NettyHttpContainerProvider.createHttp2Server(BASE_URI, resourceConfig, null);

            Runtime.getRuntime().addShutdownHook(new Thread(server::close));

            System.out.println(String.format("Application started. (HTTP/2 enabled!)\n" +
                    "Try out %s%s\nStop the application using "
                    + "CTRL+C.", BASE_URI, ROOT_PATH));
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
