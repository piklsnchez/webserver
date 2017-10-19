package org.piklsnchez.rest;

import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import javax.ws.rs.sse.OutboundSseEvent;

@Singleton
@Path("/rest")
public class Rest {
    private static final String CLASS = Rest.class.getName();
    private static final Logger LOG   = Logger.getLogger(CLASS);
    
    private String offer  = "{}";
    private String answer = "{}";
    private final Sse sse;
    private final List<SseEventSink> sinkers;
    
    public Rest(@Context final Sse sse){
        this.sse = sse;
        sinkers  = new ArrayList<>();
    }
    
    @GET
    @Path("event")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void getEvents(@Context SseEventSink eventSink){
        sinkers.add(eventSink);
    }
    
    @GET
    @Path("offer")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOffer(){
        return offer;
    }
    
    @POST
    @Path("offer")
    public String setOffer(String body){
        LOG.entering(CLASS, "setOffer", body);
        offer = body;
        sinkers.forEach(s -> s.send(sse.newEventBuilder().name("offer").data(String.class, offer).build()));
        LOG.exiting(CLASS, "setOffer");
        return null;
    }

    @GET
    @Path("answer")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnswer(){
        LOG.entering(CLASS, "getAnswer");
        LOG.exiting(CLASS, "getAnswer", answer);
        return answer;
    }
    
    @POST
    @Path("answer")
    public String setAnswer(String body){
        LOG.entering(CLASS, "setAnswer", body);
        answer = body;
        sinkers.forEach(s -> s.send(sse.newEventBuilder().name("answer").data(String.class, answer).build()));
        LOG.exiting(CLASS, "setAnswer");
        return null;
    }
}