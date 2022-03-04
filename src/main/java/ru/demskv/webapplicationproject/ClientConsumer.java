package ru.demskv.webapplicationproject;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.sse.SseEventSource;
import static ru.demskv.webapplicationproject.ServerMock.BASE_PATH;

public class ClientConsumer {

    public static final Client CLIENT = ClientBuilder.newClient();
    public static final WebTarget WEB_TARGET
            = CLIENT.target(ServerMock.CONTEXT
                    + BASE_PATH);

    public static void main(String[] args) {
        consume();
    }

    private static void consume() {
        try (final SseEventSource sseSource
                = SseEventSource
                        .target(WEB_TARGET)
                        .build()) {
                    sseSource.register(System.out::println);
                    sseSource.open();
                    for (int counter = 0; counter < 5; counter++) {
                        System.out.println(" ");
                        for (int innerCounter = 0; innerCounter < 5;
                                innerCounter++) {
                            Response post = WEB_TARGET.request().post(Entity.json("event "
                                    + innerCounter));
                        }
                        Thread.sleep(1000);
                    }
                    CLIENT.close();
                    System.out.println("\n All messages consumed");
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
    }
}