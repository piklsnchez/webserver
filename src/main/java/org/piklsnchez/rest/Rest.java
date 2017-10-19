package org.piklsnchez.rest;

import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/rest")
public class Rest {
    private static final String CLASS = Rest.class.getName();
    private static final Logger LOG   = Logger.getLogger(CLASS);
    
    private String offer  = "{}";
    private String answer = "{}";
    
    
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
        LOG.exiting(CLASS, "setAnswer");
        return null;
    }
}