package org.piklsnchez;

import java.util.stream.Stream;
import java.io.IOException;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

public class Server {
    private static final String CLASS = Server.class.getName();
    private static final Logger LOG = Logger.getLogger(CLASS);
    
    private static String offer  = "";
    private static String answer = "";
    
    public static void main(String... args){
        HttpServer server = HttpServer.createSimpleServer();
        server.getServerConfiguration().addHttpHandler(
            new HttpHandler(){
                @Override
                public void service(Request request, Response response) throws Exception {
                    LOG.entering(HttpHandler.class.getName(), "service", Stream.of(request, response).toArray());
                    
                    switch(request.getMethod().getMethodString()){
                        case "GET":
                            response.setContentType("application/json");
                            response.setContentLength(offer.length());
                            response.getWriter().write(offer);
                        break;
                        case "POST":
                            try{
                                String body = request.getPostBody(255).toStringContent();
                                LOG.info(body);
                                offer = body;
                                //wait for an answer before returning
                                while(answer.isEmpty()){
                                    try{
                                        Thread.sleep(1000);
                                    } catch(InterruptedException e){}
                                }
                                response.setContentType("application/json");
                                response.setContentLength(answer.length());
                                response.getWriter().write(answer);
                            } catch(IOException e){
                                LOG.throwing(CLASS, "service", e);
                                throw e;
                            }
                        break;
                    }
                    LOG.exiting(HttpHandler.class.getName(), "service");
                }
            }
            , "/offer"
        );
        server.getServerConfiguration().addHttpHandler(
            new HttpHandler(){
                @Override
                public void service(Request request, Response response) throws Exception {
                    LOG.entering(HttpHandler.class.getName(), "service", Stream.of(request, response).toArray());
                    
                    switch(request.getMethod().getMethodString()){
                        case "GET":
                            response.setContentType("application/json");
                            response.setContentLength(answer.length());
                            response.getWriter().write(offer);
                        break;
                        case "POST":
                            try{
                                String body = request.getPostBody(255).toStringContent();
                                LOG.info(body);
                                answer = body;
                            } catch(IOException e){
                                LOG.throwing(CLASS, "service", e);
                                throw e;
                            }
                        break;
                    }
                    LOG.exiting(HttpHandler.class.getName(), "service");
                }
            }
            , "/answer"
        );
        try {
            server.start();
            System.out.println("Press any key to stop the server...");
            System.in.read();
        } catch (Exception e) {
            LOG.severe(e.toString());
        }
    }
}
