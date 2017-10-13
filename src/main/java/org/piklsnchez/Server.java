package org.piklsnchez;

import java.io.IOException;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

public class Server {
    private static final String CLASS = Server.class.getName();
    private static final Logger LOG = Logger.getLogger(CLASS);
    
    private static String sdp;
    
    public static void main(String... args){
        HttpServer server = HttpServer.createSimpleServer();
        server.getServerConfiguration().addHttpHandler(
            new HttpHandler(){
                @Override
                public void service(Request request, Response response) throws Exception {
                    switch(request.getMethod().getMethodString()){
                        case "GET":
                            response.setContentType("text/plain");
                            response.setContentLength(sdp.length());
                            response.getWriter().write(sdp);
                            break;
                        case "POST":
                            try{
                                String body = request.getPostBody(255).toStringContent();
                                LOG.info(body);
                                sdp = body;
                            } catch(IOException e){
                                LOG.throwing(CLASS, "service", e);
                                throw e;
                            }
                            break;
                    }
                }
            }
            , "/descriptor"
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
