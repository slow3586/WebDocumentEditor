
package ru.demskv.webapplicationproject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import static jakarta.ws.rs.core.MediaType.SERVER_SENT_EVENTS;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;
import java.io.IOException;


@Path(ServerMock.BASE_PATH)
public class SseResource {
    private static volatile SseEventSink SINK = null;
    
    @GET
    @Produces(SERVER_SENT_EVENTS)
    public void getMessageQueue(@Context SseEventSink sink) {
        SseResource.SINK = sink;
    }

    @POST
    public void addMessage(final String message, @Context Sse sse)
    throws IOException {
            System.out.println("TEST");
            if (SINK != null) {
                SINK.send(sse.newEventBuilder()
                .name("sse-message")
                .id(String.valueOf(System.currentTimeMillis()))
                .data(String.class, message)
                .comment("")
                .build());
            }
        }
    }